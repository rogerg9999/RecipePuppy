package com.blacklift.recipepuppy.di.components;

/**
 * Created by rogergarzon on 22/11/17.
 */


import android.app.Application;

import com.blacklift.recipepuppy.di.modules.ActivityModule;
import com.blacklift.recipepuppy.di.modules.AppModule;
import com.blacklift.recipepuppy.ui.App;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        ActivityModule.class
})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance Builder application(Application application);
        AppComponent build();
    }
    void inject(App app);
}