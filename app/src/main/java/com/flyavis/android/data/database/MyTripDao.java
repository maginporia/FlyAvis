package com.flyavis.android.data.database;

import java.util.List;
import java.util.Set;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Flowable;

@Dao
public interface MyTripDao {
    @Query("SELECT * FROM mytrip ORDER BY myTripId DESC")
    Flowable<List<MyTrip>> getMyTrips();

    @Query("SELECT * FROM mytrip WHERE myTripId IN (:id)")
    Flowable<MyTrip> getSpecificTrip(int id);

    @Query("SELECT myTripId, tripName FROM mytrip ORDER BY myTripId DESC")
    Flowable<List<SimplifyMyTrip>> getSimplifyMyTrip();

    @Insert
    void insertTrip(MyTrip myTrip);

    @Update
    void updateTrip(MyTrip myTrip);

    @Query("DELETE FROM mytrip WHERE myTripId IN (:set)")
    void deleteTrip(Set<Integer> set);
}
