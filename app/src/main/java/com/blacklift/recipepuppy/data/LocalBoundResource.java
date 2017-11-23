package com.blacklift.recipepuppy.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.blacklift.recipepuppy.data.api.model.ApiResponse;
import com.blacklift.recipepuppy.domain.model.Resource;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rogergarzon on 22/11/17.
 */

public abstract class LocalBoundResource<Model, Domain> {


    private final MediatorLiveData<Resource<Domain>> result = new MediatorLiveData<>();
    Domain nodata = null;

    public LocalBoundResource() {
        fetchAndSave();
    }

    private void fetchAndSave() {
        result.postValue(Resource.loading(nodata));
        final LiveData<Model> dbSource = loadFromDb();
        result.addSource(dbSource, newData -> setValue(Resource.success(processData(newData))));
    }

    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

    private void setValue(Resource<Domain> newValue) {
        if (!equals(result.getValue(), newValue)) {
            result.postValue(newValue);
        }
    }


    protected abstract Domain processData(Model list);

    @NonNull
    protected abstract LiveData<Model> loadFromDb();

    public LiveData<Resource<Domain>> asLiveData() {
        return result;
    }
}