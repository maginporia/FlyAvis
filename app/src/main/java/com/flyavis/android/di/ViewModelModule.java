package com.flyavis.android.di;

import com.flyavis.android.ui.ViewModelFactory;
import com.flyavis.android.ui.addnewbill.AddNewBillViewModel;
import com.flyavis.android.ui.addnewtrip.AddNewTripViewModel;
import com.flyavis.android.ui.billdetail.BillDetailViewModel;
import com.flyavis.android.ui.bills.BillsViewModel;
import com.flyavis.android.ui.mytrips.MyTripsViewModel;
import com.flyavis.android.ui.planhelper.PlanHelperViewModel;
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
    @IntoMap
    @ViewModelKey(PlanHelperViewModel.class)
    abstract ViewModel bindPlanHelperViewModel(PlanHelperViewModel planHelperViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(BillsViewModel.class)
    abstract ViewModel bindBillsViewModel(BillsViewModel billsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(BillDetailViewModel.class)
    abstract ViewModel bindBillDetailViewModel(BillDetailViewModel billDetailViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AddNewBillViewModel.class)
    abstract ViewModel bindAddNewBillViewModel(AddNewBillViewModel addNewBillViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
