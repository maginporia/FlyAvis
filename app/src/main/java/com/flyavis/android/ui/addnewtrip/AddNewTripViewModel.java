package com.flyavis.android.ui.addnewtrip;

import com.flyavis.android.data.FlyAvisRepository;
import com.flyavis.android.data.database.MyTrip;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;
import io.reactivex.Completable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class AddNewTripViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private FlyAvisRepository repository;

    @Inject
    AddNewTripViewModel(FlyAvisRepository repository) {
        this.repository = repository;
    }

    void insertNewTrip(final MyTrip myTrip) {
        //RxJava2
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                // UserDAO.insert(...)
                repository.insetMyTrip(myTrip);
            }
        })
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
