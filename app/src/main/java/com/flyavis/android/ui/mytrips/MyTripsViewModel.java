package com.flyavis.android.ui.mytrips;

import com.flyavis.android.data.MyTripRepository;
import com.flyavis.android.data.Resource;
import com.flyavis.android.data.database.MyTrip;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public class MyTripsViewModel extends ViewModel {
    private MyTripRepository repository;
    private final LiveData<Resource<List<MyTrip>>> mMyTripData;
    private MediatorLiveData liveDataMerger = new MediatorLiveData<>();

    @Inject
    MyTripsViewModel(MyTripRepository repository) {
        this.repository = repository;

        mMyTripData = repository.getMyTripData();
        liveDataMerger.addSource(mMyTripData, value -> liveDataMerger.setValue(value));
    }

    void refresh() {
        liveDataMerger.removeSource(mMyTripData);
        liveDataMerger.addSource(mMyTripData, value -> liveDataMerger.setValue(value));
    }

    LiveData<Resource<List<MyTrip>>> getMyTripData() {
        return liveDataMerger;
    }

    void deleteMyTrip(final Set<Integer> set) {
        //RxJava2
        Completable.fromAction(() -> repository.deleteMyTrip(set))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

}
