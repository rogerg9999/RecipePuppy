package com.blacklift.recipepuppy.di.modules;


import com.blacklift.recipepuppy.ui.ItemDetailActivity;
import com.blacklift.recipepuppy.ui.ItemListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract ItemListActivity contributeItemListActivity();

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract ItemDetailActivity contributeItemDetailActivity();
}