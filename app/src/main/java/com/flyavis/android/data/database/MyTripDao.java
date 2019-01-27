package com.flyavis.android.data.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
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

    @Delete
    void deleteTrip(MyTrip myTrip);
}
