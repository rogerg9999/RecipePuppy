package com.blacklift.recipepuppy.domain.interactor;

import android.arch.lifecycle.LiveData;

import com.blacklift.recipepuppy.data.SearchRepository;
import com.blacklift.recipepuppy.domain.model.Resource;
import com.blacklift.recipepuppy.domain.model.Search;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by rogergarzon on 22/11/17.
 */

public class SearchUseCase implements LiveDataUseCaseWithParameter<String, Resource<List<Search>>> {
    private SearchRepository searchRepository;

    @Inject
    public SearchUseCase(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    @Override
    public LiveData<Resource<List<Search>>> execute(String query) {
        return searchRepository.search(query);
    }
}
