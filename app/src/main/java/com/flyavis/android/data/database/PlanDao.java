package com.flyavis.android.data.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import io.reactivex.Flowable;

@Dao
public abstract class PlanDao {

    @Query("SELECT * FROM `Plan` WHERE tripId IN (:tripId) AND planDay IN(:day) ORDER BY `spotOrder`")
    public abstract Flowable<List<Plan>> getPlannings(int tripId, int day);

    @Insert
    public abstract void insetNewSpot(Plan plan);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertSpots(List<Plan> plan);

    @Delete
    public abstract void deleteDaySpots(List<Plan> plan);

    @Transaction
    public void deleteAndInsert(List<Plan> plan) {
        deleteDaySpots(plan);
        insertSpots(plan);
    }

}
