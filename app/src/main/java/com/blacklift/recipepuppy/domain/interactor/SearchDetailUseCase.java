package com.blacklift.recipepuppy.domain.interactor;

import android.arch.lifecycle.LiveData;

import com.blacklift.recipepuppy.data.SearchRepository;
import com.blacklift.recipepuppy.domain.model.Resource;
import com.blacklift.recipepuppy.domain.model.Search;

import javax.inject.Inject;

/**
 * Created by rogergarzon on 22/11/17.
 */

public class SearchDetailUseCase implements LiveDataUseCaseWithParameter<String, Resource<Search>> {
    private SearchRepository searchRepository;

    @Inject
    public SearchDetailUseCase(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    @Override
    public LiveData<Resource<Search>> execute(String url) {
        return searchRepository.findByUrl(url);
    }
}
