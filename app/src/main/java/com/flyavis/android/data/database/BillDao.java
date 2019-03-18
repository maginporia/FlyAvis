package com.flyavis.android.data.database;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface BillDao {

    @Insert
    void insertNewBill(Bill bill);
}
