package com.flyavis.android.data.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface BillDao {

    @Insert
    Single<Long> insertNewBill(Bill bill);

    @Query("SELECT * FROM Bill ORDER BY costDate DESC")
    Flowable<List<Bill>> getBills();

    @Delete
    void deleteBill(Bill bill);
}
