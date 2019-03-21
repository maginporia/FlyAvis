package com.flyavis.android.di;

import com.flyavis.android.ui.MainActivity;
import com.flyavis.android.ui.addnewbill.AddNewBillFragment;
import com.flyavis.android.ui.addnewtrip.AddNewTripFragment;
import com.flyavis.android.ui.billdetail.BillDetailFragment;
import com.flyavis.android.ui.bills.BillsFragment;
import com.flyavis.android.ui.checklist.ChecklistFragment;
import com.flyavis.android.ui.member.MemberFragment;
import com.flyavis.android.ui.mytrips.MyTripsFragment;
import com.flyavis.android.ui.planhelper.PlanHelperFragment;
import com.flyavis.android.ui.planning.PlanningFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

//要注入的View
@Module
abstract class ViewBuilder {
    @ContributesAndroidInjector
    abstract MyTripsFragment bindMyTripsFragment();

    @ContributesAndroidInjector
    abstract AddNewTripFragment addNewTripFragment();

    @ContributesAndroidInjector
    abstract PlanningFragment planningFragment();

    @ContributesAndroidInjector
    abstract PlanHelperFragment planHelperFragment();

    @ContributesAndroidInjector
    abstract BillsFragment billsFragment();

    @ContributesAndroidInjector
    abstract BillDetailFragment billDetailFragment();

    @ContributesAndroidInjector
    abstract AddNewBillFragment addNewBillFragment();

    @ContributesAndroidInjector
    abstract MemberFragment memberFragment();

    @ContributesAndroidInjector
    abstract ChecklistFragment checklistFragment();

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();
}
