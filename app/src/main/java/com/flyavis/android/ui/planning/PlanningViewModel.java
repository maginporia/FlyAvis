package com.flyavis.android.ui.planning;

import com.flyavis.android.data.FlyAvisRepository;
import com.flyavis.android.data.database.Plan;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PlanningViewModel extends ViewModel {
    private FlyAvisRepository repository;
    private LiveData<List<Plan>> planningData;
    private MediatorLiveData liveDataMerger = new MediatorLiveData<>();

    @Inject
    PlanningViewModel(FlyAvisRepository repository) {
        this.repository = repository;
    }

    LiveData<List<Plan>> getPlanningData(int tripId, int day) {
        //RxJava2 Flowable>LiveData
        Flowable<List<Plan>> flowable = repository.getPlannings(tripId, day)
                .observeOn(AndroidSchedulers.mainThread());
        planningData = LiveDataReactiveStreams.fromPublisher(flowable);
        liveDataMerger.addSource(planningData, value -> liveDataMerger.setValue(value));
        return liveDataMerger;
    }

    void insetNewSpot(Plan plan) {
        //RxJava2
        Completable.fromAction(() -> repository.insertSpot(plan))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    void updateSpotOrder(int order, int tripId, int day) {
        Completable.fromAction(() -> repository.updateSpotOrder(order, tripId, day))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
