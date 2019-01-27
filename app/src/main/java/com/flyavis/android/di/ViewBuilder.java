package com.flyavis.android.di;

import com.flyavis.android.ui.MainActivity;
import com.flyavis.android.ui.addnewtrip.AddNewTripFragment;
import com.flyavis.android.ui.mytrips.MyTripsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ViewBuilder {
    @ContributesAndroidInjector
    abstract MyTripsFragment bindMyTripsFragment();
    @ContributesAndroidInjector
    abstract AddNewTripFragment addNewTripFragment();
    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();
}
