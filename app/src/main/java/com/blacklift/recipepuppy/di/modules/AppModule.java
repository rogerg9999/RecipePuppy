package com.blacklift.recipepuppy.di.modules;

/**
 * Created by rogergarzon on 22/11/17.
 */


import android.app.Application;
import android.arch.persistence.room.Room;

import com.blacklift.recipepuppy.data.api.SearchService;
import com.blacklift.recipepuppy.data.db.AppDatabase;
import com.blacklift.recipepuppy.data.db.SearchDAO;
import com.blacklift.recipepuppy.ui.App;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {


    @Singleton @Provides
    SearchService provideSearchService() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        return new Retrofit.Builder()
                .baseUrl("http://www.recipepuppy.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
                .create(SearchService.class);
    }

    @Singleton
    @Provides
    AppDatabase providesAppDatabase(Application application){
        return Room.databaseBuilder(application, AppDatabase.class, "database").build();
    }

    @Provides
    SearchDAO providesSearchDAO(AppDatabase appDatabase){
        return appDatabase.searchDAO();
    }


}