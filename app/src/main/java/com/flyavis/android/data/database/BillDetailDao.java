package com.flyavis.android.data.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Flowable;

@Dao
public interface BillDetailDao {

    @Insert
    void insertNewBillDetail(BillDetail billDetail);

    @Query("SELECT * FROM BillDetail")
    Flowable<List<BillDetail>> getBillDetails();

    @Delete
    void deleteBillDetal(Bill billDetail);
}
