package com.flyavis.android.ui.addnewtrip;

import com.flyavis.android.data.MyTripRepository;
import com.flyavis.android.data.database.MyTrip;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class AddNewTripViewModel extends ViewModel {
    private final MyTripRepository myTripRepository;
    private LiveData<MyTrip> listLiveData;
    public MutableLiveData<String> tripName = new MutableLiveData<>();
    public MutableLiveData<String> startDate = new MutableLiveData<>();
    public MutableLiveData<String> endDate = new MutableLiveData<>();

    @Inject
    AddNewTripViewModel(MyTripRepository myTripRepository) {
        this.myTripRepository = myTripRepository;

    }

    void insertTrip(MyTrip myTrip) {
        //RxJava2
        Completable.fromAction(() -> myTripRepository.insetMyTrip(myTrip))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    void updateTrip(MyTrip myTrip) {
        //RxJava2
        Completable.fromAction(() -> myTripRepository.updateMyTrip(myTrip))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    LiveData<MyTrip> getSpecificTrip() {
        return listLiveData;
    }

    void insetMyTripRemote(MyTrip myTrip) {
//        Completable.fromAction(() -> myTripRepository.insetMyTripRemote(myTrip))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new DisposableCompletableObserver() {
//                               @Override
//                               public void onComplete() {
//                                   Timber.d("ok");
//                               }
//
//                               @Override
//                               public void onError(Throwable e) {
//                                   Timber.d("error");
//                               }
//                           });
        myTripRepository.insetMyTripRemote(myTrip);
    }

    void setTripId(int tripId) {
        Flowable<MyTrip> flowable = myTripRepository.getSpecificTrip(tripId)
                .observeOn(AndroidSchedulers.mainThread());
        listLiveData = LiveDataReactiveStreams.fromPublisher(flowable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Timber.d("clear!");
    }
}
