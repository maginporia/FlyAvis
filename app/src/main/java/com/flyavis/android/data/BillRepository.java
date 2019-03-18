package com.flyavis.android.data;

import com.flyavis.android.data.database.Bill;
import com.flyavis.android.data.database.BillDao;

import javax.inject.Inject;

public class BillRepository {
    private final BillDao billDao;

    @Inject
    BillRepository(BillDao billDao) {
        this.billDao = billDao;
    }

    public void insertNewBill(Bill bill) {
        billDao.insertNewBill(bill);
    }
}
