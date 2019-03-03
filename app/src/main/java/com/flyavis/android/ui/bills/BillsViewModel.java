package com.flyavis.android.ui.bills;

import com.flyavis.android.data.MyTripRepository;
import com.flyavis.android.data.PlanRepository;
import com.flyavis.android.data.database.MyTrip;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

public class BillsViewModel extends ViewModel {
    private MyTripRepository myTripRepository;
    private PlanRepository planRepository;

    @Inject
    BillsViewModel(MyTripRepository myTripRepository, PlanRepository planRepository) {
        this.myTripRepository = myTripRepository;
        this.planRepository = planRepository;
    }

    LiveData<List<MyTrip>> getMyTrips() {
        return LiveDataReactiveStreams.fromPublisher(myTripRepository.getMyTrips());
    }

    LiveData<List<Integer>> getPlanId(int tripId) {
        return LiveDataReactiveStreams.fromPublisher(planRepository.getPlanId(tripId));
    }

}
