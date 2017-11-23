package com.blacklift.recipepuppy.data.api;

import com.blacklift.recipepuppy.data.api.model.SearchResponse;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by rogergarzon on 22/11/17.
 */

public interface SearchService {
    @GET("/api")
    Single<Response<SearchResponse>> search(@Query("q") String query);
}
