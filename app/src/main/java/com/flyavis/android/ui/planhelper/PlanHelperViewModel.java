package com.flyavis.android.ui.planhelper;

import com.flyavis.android.data.PlanRepository;
import com.flyavis.android.data.database.Plan;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;
import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public class PlanHelperViewModel extends ViewModel {
    private PlanRepository repository;
    @Inject
    PlanHelperViewModel(PlanRepository repository) {
        this.repository = repository;
    }

    LiveData<Plan> getPlanData(int planId) {
        return LiveDataReactiveStreams.fromPublisher(repository.getPlan(planId));
    }

    void updateNotice(int planId, String notice) {
        Completable.fromAction(() -> repository.updateNotice(planId, notice))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    void updateBudget(int planId, int cost, int traffic) {
        Completable.fromAction(() -> repository.updateBudget(planId, cost, traffic))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
