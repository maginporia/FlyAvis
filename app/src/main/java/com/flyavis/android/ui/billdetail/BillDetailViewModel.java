package com.flyavis.android.ui.billdetail;

import com.flyavis.android.data.BillRepository;
import com.flyavis.android.data.database.Bill;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

public class BillDetailViewModel extends ViewModel {
    private BillRepository billRepository;
    @Inject
    BillDetailViewModel(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    LiveData<List<Bill>> getBills(int tripId) {
        return LiveDataReactiveStreams.fromPublisher(billRepository.getBills(tripId));
    }

}
