package com.flyavis.android.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@TypeConverters({DateConverter.class, TimeConverter.class})
@Database(entities = {MyTrip.class, Plan.class, Bill.class}, version = 1)
public abstract class FlyAvisDb extends RoomDatabase {
    public abstract MyTripDao myTripDao();

    public abstract PlanDao planningDao();

    public abstract BillDao billDao();
}
