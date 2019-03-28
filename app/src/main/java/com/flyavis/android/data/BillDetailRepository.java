package com.flyavis.android.data;

import com.flyavis.android.data.database.BillDetail;
import com.flyavis.android.data.database.BillDetailDao;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class BillDetailRepository {
    private final BillDetailDao billDetailDao;

    @Inject
    BillDetailRepository(BillDetailDao billDetailDao) {
        this.billDetailDao = billDetailDao;
    }

    public void insertNewBillDetail(BillDetail billDetail) {
        billDetailDao.insertNewBillDetail(billDetail);
    }

    public Flowable<List<BillDetail>> getBillDetail(int tripId) {
        return billDetailDao.getBillDetails(tripId);
    }
}
