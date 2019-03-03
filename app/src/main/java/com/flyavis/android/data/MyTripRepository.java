package com.flyavis.android.data;

import com.flyavis.android.data.database.MyTrip;
import com.flyavis.android.data.database.MyTripDao;
import com.flyavis.android.data.network.FlyAvisService;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

//處理本地與遠端資料
@Singleton
public class MyTripRepository {
    private final FlyAvisService service;
    private final MyTripDao myTripDao;

    @Inject
    MyTripRepository(FlyAvisService service, MyTripDao myTripDao) {
        this.service = service;
        this.myTripDao = myTripDao;
    }

    public LiveData<Resource<List<MyTrip>>> getMyTripData() {
        return new NetworkBoundResource<List<MyTrip>, MyTrip>() {
            @Override
            protected void saveCallResult(@NonNull MyTrip item) {

            }

            @Override
            protected boolean shouldFetch() {
                return false;
            }

            @NonNull
            @Override
            protected Flowable<List<MyTrip>> loadFromDb() {
                return getMyTrips();
            }

            @NonNull
            @Override
            protected Observable<MyTrip> createCall() {
                return null;
            }
        }.getAsLiveData();
    }

    public Flowable<List<MyTrip>> getMyTrips() {
        return myTripDao.getMyTrips();
    }

    public Flowable<MyTrip> getSpecificTrip(int myTripId) {
        return myTripDao.getSpecificTrip(myTripId);
    }

    public void insetMyTrip(MyTrip myTrip) {
        myTripDao.insertTrip(myTrip);

    }

    public void updateMyTrip(MyTrip myTrip) {
        myTripDao.updateTrip(myTrip);
    }

    public void deleteMyTrip(Set<Integer> set) {
        myTripDao.deleteTrip(set);
    }

    public void insetMyTripRemote(MyTrip myTrip) {
        service.insertMyTrips(myTrip)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}
