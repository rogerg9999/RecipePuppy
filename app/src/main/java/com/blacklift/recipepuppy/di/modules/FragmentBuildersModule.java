package com.blacklift.recipepuppy.di.modules;



import com.blacklift.recipepuppy.ui.ItemDetailFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract ItemDetailFragment contributeItemFragment();

}