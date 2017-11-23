package com.blacklift.recipepuppy.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.blacklift.recipepuppy.data.api.model.ApiResponse;
import com.blacklift.recipepuppy.domain.model.Resource;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rogergarzon on 22/11/17.
 */

public abstract class NetworkBoundResource<Api, Model, Domain> {


    private final MediatorLiveData<Resource<Domain>> result = new MediatorLiveData<>();
    Domain nodata = null;

    public NetworkBoundResource() {
        fetchAndSave();
    }

    private void fetchAndSave() {
        result.postValue(Resource.loading(nodata));
        final LiveData<Model> dbSource = loadFromDb();
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource, newData -> setValue(Resource.success(processData(newData))));
            }
        });
    }

    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

    private void setValue(Resource<Domain> newValue) {
        if (!equals(result.getValue(), newValue)) {
            result.postValue(newValue);
        }
    }

    private void fetchFromNetwork(final LiveData<Model> dbSource) {
        final Single<ApiResponse<Api>> apiResponse = createCall();
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource, newData -> setValue(Resource.loading(processData(newData))));

        apiResponse
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isSuccessful()) {
                        result.removeSource(dbSource);
                        result.addSource(dbSource, models -> {
                            result.removeSource(dbSource);
                            saveCompletableCallResult(processResponse(response))
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> result.addSource(loadFromDb(), newData -> setValue(Resource.success(processData(newData)))), throwable -> onError(dbSource, throwable.getMessage()));
                        });
                    } else {
                        onError(dbSource, response.errorMessage);
                    }

                }, throwable -> onError(dbSource, throwable.getMessage()));


    }

    protected void onError(final LiveData<Model> dbSource, final String errorMessage) {
        result.removeSource(dbSource);
        onFetchFailed();
        result.addSource(dbSource, newData -> {
            setValue(Resource.error(errorMessage, processData(newData)));
        });
    }


    protected void onFetchFailed() {
    }

    public LiveData<Resource<Domain>> asLiveData() {
        return result;
    }

    protected abstract Model processResponse(ApiResponse<Api> response);

    protected abstract Domain processData(Model list);

    protected Completable saveCompletableCallResult(@NonNull final Model items) {
        return Completable.fromAction(() -> saveCallResult(items));
    }


    protected abstract void saveCallResult(@NonNull Model items);

    protected abstract boolean shouldFetch(@Nullable Model data);

    @NonNull
    protected abstract LiveData<Model> loadFromDb();

    @NonNull
    protected abstract Single<ApiResponse<Api>> createCall();


}