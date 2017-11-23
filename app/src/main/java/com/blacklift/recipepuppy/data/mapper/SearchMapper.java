package com.blacklift.recipepuppy.data.mapper;

import com.blacklift.recipepuppy.data.api.model.ApiSearch;
import com.blacklift.recipepuppy.data.db.SearchModel;
import com.blacklift.recipepuppy.domain.model.Search;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by rogergarzon on 22/11/17.
 */

public class SearchMapper {

    @Inject
    public SearchMapper() {
    }

    public SearchModel mapApiToModel(ApiSearch searchResult, String query){
        SearchModel search = new SearchModel(query, searchResult.getTitle(), searchResult.getHref(), searchResult.getIngredients(), searchResult.getThumbnail());
        return search;
    }

    public Search mapModelToDomain(SearchModel searchResult){
        Search search = new Search(searchResult.getTitle(), searchResult.getHref(), searchResult.getIngredients(), searchResult.getThumbnail());
        return search;
    }


    public List<SearchModel> mapListApiToModel(List<ApiSearch> list, String query){
        List<SearchModel> out = new ArrayList<>();
        for(ApiSearch searchResult : list){
            out.add(mapApiToModel(searchResult, query));
        }
        return out;
    }

    public List<Search> mapListModelToDomain(List<SearchModel> list){
        List<Search> out = new ArrayList<>();
        for(SearchModel searchResult : list){
            out.add(mapModelToDomain(searchResult));
        }
        return out;
    }

}
