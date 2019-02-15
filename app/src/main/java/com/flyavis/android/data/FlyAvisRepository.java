package com.flyavis.android.data;

import com.flyavis.android.data.database.MyTrip;
import com.flyavis.android.data.database.MyTripDao;
import com.flyavis.android.data.database.Plan;
import com.flyavis.android.data.database.PlanDao;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

//處理本地與遠端資料
@Singleton
public class FlyAvisRepository {
    private final MyTripDao myTripDao;
    private final PlanDao planDao;

    @Inject
    FlyAvisRepository(MyTripDao myTripDao, PlanDao planDao) {
        this.myTripDao = myTripDao;
        this.planDao = planDao;
    }

    public Flowable<List<MyTrip>> getMyTripData() {
        return myTripDao.getMyTrips();
    }

    public Flowable<MyTrip> getSpecificTrip(int myTripId) {
        return myTripDao.getSpecificTrip(myTripId);
    }

    public void insetMyTrip(MyTrip myTrip) {
        myTripDao.insertTrip(myTrip);
    }

    public void deleteMyTrip(Set<Integer> set) {
        myTripDao.deleteTrip(set);
    }

    public void insertSpot(Plan plan) {
        planDao.insetNewSpot(plan);
    }

    public Flowable<List<Plan>> getPlannings(int tripId, int day) {
        return planDao.getPlannings(tripId, day);
    }

    public void updateSpotOrder(int order, int tripId, int day) {
        planDao.updateSpotOrder(order, tripId, day);
    }
}
