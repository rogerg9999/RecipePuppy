package com.blacklift.recipepuppy.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by rogergarzon on 22/11/17.
 */
@Database(version = 1, entities = SearchModel.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SearchDAO searchDAO();
}
