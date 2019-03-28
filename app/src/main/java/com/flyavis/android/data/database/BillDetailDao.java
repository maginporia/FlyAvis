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

    @Query("SELECT * FROM BillDetail bd INNER JOIN Bill b ON bd.billId = b.billId WHERE b.tripId =(:tripId) " +
            "ORDER BY b.costDate DESC")
    Flowable<List<BillDetail>> getBillDetails(int tripId);

    @Query("SELECT * FROM BillDetail bd INNER JOIN Bill b ON bd.billId = b.billId WHERE b.tripId =(:tripId) " +
            "ORDER BY b.costDate DESC LIMIT 3")
    Flowable<List<BillDetail>> getBillDetailsLimit(int tripId);

    @Delete
    void deleteBillDetail(Bill billDetail);
}
