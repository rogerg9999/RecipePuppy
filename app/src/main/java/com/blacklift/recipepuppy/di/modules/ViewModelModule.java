package com.blacklift.recipepuppy.di.modules;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.blacklift.recipepuppy.di.viewmodel.ViewModelFactory;
import com.blacklift.recipepuppy.di.viewmodel.ViewModelKey;
import com.blacklift.recipepuppy.ui.SearchViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by rogergarzon on 22/11/17.
 */


@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    abstract ViewModel bindSearchViewModel(SearchViewModel searchViewModel);


    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}