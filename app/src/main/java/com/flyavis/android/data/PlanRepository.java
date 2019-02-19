package com.flyavis.android.data;

import com.flyavis.android.data.database.Plan;
import com.flyavis.android.data.database.PlanDao;
import com.flyavis.android.data.network.FlyAvisService;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import io.reactivex.Flowable;
import io.reactivex.Observable;

public class PlanRepository {
    private final FlyAvisService service;
    private final PlanDao planDao;

    @Inject
    PlanRepository(FlyAvisService service, PlanDao planDao) {
        this.service = service;
        this.planDao = planDao;
    }

    public LiveData<Resource<List<Plan>>> getPlannings(int tripId, int day) {
        return new NetworkBoundResource<List<Plan>, Plan>() {
            @Override
            protected void saveCallResult(@NonNull Plan item) {

            }

            @Override
            protected boolean shouldFetch() {
                return false;
            }

            @NonNull
            @Override
            protected Flowable<List<Plan>> loadFromDb() {
                return planDao.getPlannings(tripId, day);
            }

            @NonNull
            @Override
            protected Observable<Plan> createCall() {
                return null;
            }
        }.getAsLiveData();
    }

    public void insertSpot(Plan plan) {
        planDao.insetNewSpot(plan);
    }

    public void updateSpotOrders(List<Plan> plan) {
        planDao.deleteAndInsert(plan);
    }
}
