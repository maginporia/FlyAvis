package com.flyavis.android.ui.planning;

import com.flyavis.android.data.PlanRepository;
import com.flyavis.android.data.Resource;
import com.flyavis.android.data.database.Plan;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public class PlanningViewModel extends ViewModel {
    private PlanRepository repository;
    private LiveData<Resource<List<Plan>>> planningData;
    private MediatorLiveData liveDataMerger = new MediatorLiveData<>();

    @Inject
    PlanningViewModel(PlanRepository repository) {
        this.repository = repository;
    }

    LiveData<Resource<List<Plan>>> getPlanningData(int tripId, int day) {
        planningData = repository.getPlannings(tripId, day);
        liveDataMerger.addSource(planningData, value -> liveDataMerger.setValue(value));
        return liveDataMerger;
    }

    void insetNewSpot(Plan plan) {
        //RxJava2
        Completable.fromAction(() -> repository.insertSpot(plan))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    void updateOrders(List<Plan> plan) {
        Completable.fromAction(() -> repository.updateSpotOrders(plan))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
