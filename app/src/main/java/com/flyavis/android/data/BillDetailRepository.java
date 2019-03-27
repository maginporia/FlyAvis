package com.flyavis.android.data;

import com.flyavis.android.data.database.BillDetail;
import com.flyavis.android.data.database.BillDetailDao;

import javax.inject.Inject;

public class BillDetailRepository {
    private final BillDetailDao billDetailDao;

    @Inject
    BillDetailRepository(BillDetailDao billDetailDao) {
        this.billDetailDao = billDetailDao;
    }

    public void insertNewBillDetail(BillDetail billDetail) {
        billDetailDao.insertNewBillDetail(billDetail);
    }
}
