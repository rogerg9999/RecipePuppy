package com.blacklift.recipepuppy.data.api.model;

import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by rogergarzon on 22/11/17.
 */

public class ApiResponse<T> {
    public final int code;
    @Nullable
    public final T body;
    @Nullable
    public final String errorMessage;

    public ApiResponse(int code, T body, String errorMessage) {
        this.code = code;
        this.body = body;
        this.errorMessage = errorMessage;
    }

    public ApiResponse(Throwable error) {
        code = 500;
        body = null;
        errorMessage = error.getMessage();
    }

    public ApiResponse(Response<T> response) {
        code = response.code();
        Log.i("Response", code + " " + response.body().toString());
        if(response.isSuccessful()) {
            body = response.body();
            errorMessage = null;
        } else {
            String message = null;
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody().string();
                } catch (IOException ignored) {
                    Log.e("error parsing response", ignored.getMessage());
                }
            }
            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }
            errorMessage = message;
            body = null;
        }

    }

    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }
}