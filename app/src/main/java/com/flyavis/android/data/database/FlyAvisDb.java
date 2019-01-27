package com.flyavis.android.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@TypeConverters(DateConverter.class)
@Database(entities = {MyTrip.class}, version = 1)
public abstract class FlyAvisDb extends RoomDatabase {
    public abstract MyTripDao myTripDao();
}
