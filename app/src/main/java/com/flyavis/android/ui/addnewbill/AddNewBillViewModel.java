package com.flyavis.android.ui.addnewbill;

import com.flyavis.android.data.PlanRepository;
import com.flyavis.android.data.database.SimplifyPlan;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class AddNewBillViewModel extends ViewModel {
    private PlanRepository planRepository;

    @Inject
    AddNewBillViewModel(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    LiveData<List<SimplifyPlan>> getSimPlifyPlan(int myTripId) {
        Flowable<List<SimplifyPlan>> flowable = planRepository.getSimplifyPlan(myTripId)
                .observeOn(AndroidSchedulers.mainThread());
        return LiveDataReactiveStreams.fromPublisher(flowable);
    }
}
