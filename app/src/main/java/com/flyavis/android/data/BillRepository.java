package com.flyavis.android.data;

import com.flyavis.android.data.database.Bill;
import com.flyavis.android.data.database.BillDao;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class BillRepository {
    private final BillDao billDao;

    @Inject
    BillRepository(BillDao billDao) {
        this.billDao = billDao;
    }

    public void insertNewBill(Bill bill) {
        billDao.insertNewBill(bill);
    }

    public void deleteBill(Bill bill) {
        billDao.deleteBill(bill);
    }

    public Flowable<List<Bill>> getBills(int tripId) {
        return billDao.getBills();
    }
}
