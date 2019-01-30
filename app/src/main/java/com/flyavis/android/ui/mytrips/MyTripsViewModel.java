package com.flyavis.android.ui.mytrips;

import com.flyavis.android.data.FlyAvisRepository;
import com.flyavis.android.data.database.MyTrip;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyTripsViewModel extends ViewModel {
    private final LiveData<List<MyTrip>> mMyTripData;
    private FlyAvisRepository repository;

    @Inject
    MyTripsViewModel(FlyAvisRepository repository) {
        this.repository = repository;

        //RxJava2 Flowable>LiveData
        Flowable<List<MyTrip>> flowable = repository.getMyTripData()
                .observeOn(AndroidSchedulers.mainThread());
        mMyTripData = LiveDataReactiveStreams.fromPublisher(flowable);
    }

    LiveData<List<MyTrip>> getMyTripData() {
        return mMyTripData;
    }

    void deleteMyTrip(final Set<Integer> set) {
        //RxJava2
        Completable.fromAction(() -> {
            repository.deleteMyTrip(set);
        })
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

}
