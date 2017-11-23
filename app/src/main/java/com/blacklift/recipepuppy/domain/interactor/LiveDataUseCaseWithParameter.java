package com.blacklift.recipepuppy.domain.interactor;

import android.arch.lifecycle.LiveData;

/**
 * Created by rogergarzon on 22/11/17.
 */

public interface LiveDataUseCaseWithParameter<P, R> {
    LiveData<R> execute(P p);
}
