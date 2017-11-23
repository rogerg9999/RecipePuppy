package com.blacklift.recipepuppy.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.blacklift.recipepuppy.domain.interactor.SearchDetailUseCase;
import com.blacklift.recipepuppy.domain.model.Resource;
import com.blacklift.recipepuppy.domain.model.Search;
import com.blacklift.recipepuppy.domain.interactor.SearchUseCase;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by rogergarzon on 22/11/17.
 */

public class SearchViewModel extends ViewModel {
    private SearchUseCase searchUseCase;
    private SearchDetailUseCase searchDetailUseCase;

    @Inject
    public SearchViewModel(SearchUseCase searchUseCase, SearchDetailUseCase searchDetailUseCase) {
        this.searchUseCase = searchUseCase;
        this.searchDetailUseCase = searchDetailUseCase;
    }

    private MutableLiveData<String> query = new MutableLiveData<>();

    private LiveData<Resource<List<Search>>> search = Transformations.switchMap(query, (q) -> searchUseCase.execute(q));


    public LiveData<Resource<List<Search>>> search(){
        return search;
    }

    public void setQuery(String q){
        query.postValue(q);
    }

    public LiveData<Resource<Search>> getDetail(String url){
        return searchDetailUseCase.execute(url);
    }
}
