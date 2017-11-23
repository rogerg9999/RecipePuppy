package com.blacklift.recipepuppy.data.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by rogergarzon on 22/11/17.
 */
@Dao
public interface SearchDAO {

    @Insert
    void insertAll(List<SearchModel> models);

    @Query("SELECT * FROM SearchModel WHERE `query` = :query")
    LiveData<List<SearchModel>> findByQuery(String query);

    @Query("DELETE FROM SearchModel WHERE `query` = :query")
    void deleteByQuery(String query);

    @Query("SELECT * FROM SearchModel WHERE href = :url")
    LiveData<SearchModel> findByUrl(String url);

}
