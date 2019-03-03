package com.flyavis.android.data.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import io.reactivex.Flowable;

@Dao
public abstract class PlanDao {

    @Query("SELECT * FROM `Plan` WHERE tripId IN (:tripId) AND planDay IN(:day) ORDER BY `spotOrder`")
    public abstract Flowable<List<Plan>> getPlannings(int tripId, int day);

    @Query("SELECT * FROM `plan` WHERE planId IN (:planId)")
    public abstract Flowable<Plan> getPlan(int planId);

    @Query("SELECT planId FROM `plan` WHERE tripId IN (:tripId)")
    public abstract Flowable<List<Integer>> getPlanId(int tripId);

    @Insert
    public abstract void insetNewSpot(Plan plan);

    @Delete
    public abstract void deletePlan(Plan plan);

    @Update
    public abstract void updatePlan(Plan plan);

    @Update
    public abstract void updatePlans(List<Plan> plan);

    @Query("UPDATE `plan` SET spotNotice = :notice WHERE planId IN (:planId)")
    public abstract void updateNotice(int planId, String notice);

    @Query("UPDATE `plan` SET spotCost = :cost AND spotTrafficFee = :traffic WHERE planId IN (:planId)")
    public abstract void updateBudget(int planId, int cost, int traffic);

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
