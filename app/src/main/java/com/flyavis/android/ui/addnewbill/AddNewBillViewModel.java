package com.flyavis.android.ui.addnewbill;

import com.flyavis.android.data.BillRepository;
import com.flyavis.android.data.PlanRepository;
import com.flyavis.android.data.database.Bill;
import com.flyavis.android.data.database.SimplifyPlan;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddNewBillViewModel extends ViewModel {
    private PlanRepository planRepository;
    private BillRepository billRepository;

    @Inject
    AddNewBillViewModel(PlanRepository planRepository, BillRepository billRepository) {
        this.planRepository = planRepository;
        this.billRepository = billRepository;
    }

    LiveData<List<SimplifyPlan>> getSimplifyPlan(int myTripId) {
        Flowable<List<SimplifyPlan>> flowable = planRepository.getSimplifyPlan(myTripId)
                .observeOn(AndroidSchedulers.mainThread());
        return LiveDataReactiveStreams.fromPublisher(flowable);
    }

    void insertNewBill(Bill bill) {
        Completable.fromAction(() -> billRepository.insertNewBill(bill))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
