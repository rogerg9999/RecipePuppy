package com.blacklift.recipepuppy.data;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.blacklift.recipepuppy.data.api.SearchService;
import com.blacklift.recipepuppy.data.api.model.ApiResponse;
import com.blacklift.recipepuppy.data.api.model.ApiSearch;
import com.blacklift.recipepuppy.data.db.SearchDAO;
import com.blacklift.recipepuppy.data.db.SearchModel;
import com.blacklift.recipepuppy.data.mapper.SearchMapper;
import com.blacklift.recipepuppy.domain.model.Resource;
import com.blacklift.recipepuppy.domain.model.Search;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by rogergarzon on 22/11/17.
 */

public class SearchRepository {
    private SearchService api;
    private SearchMapper mapper;
    private SearchDAO dao;

    @Inject
    public SearchRepository(SearchService searchService, SearchMapper mapper, SearchDAO dao) {
        this.api = searchService;
        this.mapper = mapper;
        this.dao = dao;
    }

    public LiveData<Resource<List<Search>>> search(String query){
        return new NetworkBoundResource<List<ApiSearch>, List<SearchModel>, List<Search>>(){

            @Override
            protected List<SearchModel> processResponse(ApiResponse<List<ApiSearch>> response) {
                Log.i("Results", response.body.toString());
                return mapper.mapListApiToModel(response.body, query);
            }

            @Override
            protected List<Search> processData(List<SearchModel> list) {
                return mapper.mapListModelToDomain(list);
            }

            @Override
            protected void saveCallResult(@NonNull List<SearchModel> items) {
                dao.deleteByQuery(query);
                dao.insertAll(items);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<SearchModel> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<SearchModel>> loadFromDb() {
                return dao.findByQuery(query);
            }

            @NonNull
            @Override
            protected Single<ApiResponse<List<ApiSearch>>> createCall() {
                return api.search(query)
                        .map(response -> new ApiResponse<List<ApiSearch>>(response.code(), response.body() != null ? response.body().getResults() : new ArrayList<>(), response.message()));
            }
        }.asLiveData();
    }

    public LiveData<Resource<Search>> findByUrl(String url){
       return new LocalBoundResource<SearchModel, Search>(){

           @Override
           protected Search processData(SearchModel model) {
               return mapper.mapModelToDomain(model);
           }

           @NonNull
           @Override
           protected LiveData<SearchModel> loadFromDb() {
               return dao.findByUrl(url);
           }
       }.asLiveData();
    }
}
