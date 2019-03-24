package com.flyavis.android.data.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Flowable;

@Dao
public interface BillDao {

    @Insert
    void insertNewBill(Bill bill);

    @Query("SELECT * FROM Bill")
    Flowable<List<Bill>> getBills();

    @Delete
    void deleteBill(Bill bill);
}
