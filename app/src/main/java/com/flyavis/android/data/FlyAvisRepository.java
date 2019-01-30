package com.flyavis.android.data;

import com.flyavis.android.data.database.MyTrip;
import com.flyavis.android.data.database.MyTripDao;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

@Singleton
public class FlyAvisRepository {
    private final MyTripDao myTripDao;
    @Inject
    FlyAvisRepository(MyTripDao myTripDao) {
        this.myTripDao = myTripDao;
    }

    public Flowable<List<MyTrip>> getMyTripData() {
        return myTripDao.getMyTrips();
    }

    public void insetMyTrip(MyTrip myTrip) {
       myTripDao.newTrip(myTrip);
    }

    public void deleteMyTrip(Set<Integer> set) {
        myTripDao.deleteTrip(set);
    }
}
