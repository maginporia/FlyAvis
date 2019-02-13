package com.flyavis.android.di;

import com.flyavis.android.ui.ViewModelFactory;
import com.flyavis.android.ui.addnewtrip.AddNewTripViewModel;
import com.flyavis.android.ui.mytrips.MyTripsViewModel;
import com.flyavis.android.ui.planning.PlanningViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

//要注入factory的viewModel
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
    @IntoMap
    @ViewModelKey(PlanningViewModel.class)
    abstract ViewModel bindPlanningViewModel(PlanningViewModel planningTripViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
