package com.flyavis.android.di;

import com.flyavis.android.ui.MainActivity;
import com.flyavis.android.ui.addnewtrip.AddNewTripFragment;
import com.flyavis.android.ui.mytrips.MyTripsFragment;
import com.flyavis.android.ui.planning.PlanningFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ViewBuilder {
    @ContributesAndroidInjector
    abstract MyTripsFragment bindMyTripsFragment();
    @ContributesAndroidInjector
    abstract AddNewTripFragment addNewTripFragment();

    @ContributesAndroidInjector
    abstract PlanningFragment planningFragment();
    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();
}
