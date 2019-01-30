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
    @Query("SELECT * FROM my_trip ORDER BY end_date DESC")
    Flowable<List<MyTrip>> getMyTrips();

    @Update
    Void editTrip(MyTrip myTrip);

    @Insert
    void newTrip(MyTrip myTrip);

    @Query("DELETE FROM my_trip WHERE my_trip_id IN (:set)")
    void deleteTrip(Set<Integer> set);
}
