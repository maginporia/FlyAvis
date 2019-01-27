package com.flyavis.android.di;

import com.flyavis.android.ui.ViewModelFactory;
import com.flyavis.android.ui.addnewtrip.AddNewTripViewModel;
import com.flyavis.android.ui.mytrips.MyTripsViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MyTripsViewModel.class)
    abstract ViewModel bindMyTripsViewModel(MyTripsViewModel myTripsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AddNewTripViewModel.class)
    abstract ViewModel bindAddNewTripViewModel(AddNewTripViewModel addNewTripViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
