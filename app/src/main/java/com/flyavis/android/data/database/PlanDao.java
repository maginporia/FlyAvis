package com.flyavis.android.data.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Flowable;

@Dao
public interface PlanDao {

    @Query("SELECT * FROM `Plan` WHERE trip_id IN (:tripId) AND day IN(:day)")
    Flowable<List<Plan>> getPlannings(int tripId, int day);

    @Insert
    void insetNewSpot(Plan plan);

}
