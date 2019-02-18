package com.flyavis.android.ui.addnewtrip;

import com.flyavis.android.data.MyTripRepository;
import com.flyavis.android.data.database.MyTrip;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddNewTripViewModel extends ViewModel {
    private MyTripRepository repository;

    @Inject
    AddNewTripViewModel(MyTripRepository repository) {
        this.repository = repository;
    }

    void insertTrip(final MyTrip myTrip) {
        //RxJava2
        Completable.fromAction(() -> repository.insetMyTrip(myTrip))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    LiveData<MyTrip> getSpecificTrip(int myTripId) {
        Flowable<MyTrip> flowable = repository.getSpecificTrip(myTripId)
                .observeOn(AndroidSchedulers.mainThread());
        return LiveDataReactiveStreams.fromPublisher(flowable);
    }

}
