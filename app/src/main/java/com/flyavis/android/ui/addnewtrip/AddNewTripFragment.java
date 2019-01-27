package com.flyavis.android.ui.addnewtrip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyavis.android.R;
import com.flyavis.android.data.database.MyTrip;
import com.flyavis.android.databinding.AddNewTripFragmentBinding;

import java.sql.Date;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerFragment;

public class AddNewTripFragment extends DaggerFragment {

    @Inject
    ViewModelProvider.Factory factory;
    private AddNewTripViewModel mViewModel;
    private AddNewTripFragmentBinding binding;

    public static AddNewTripFragment newInstance() {
        return new AddNewTripFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil
                .inflate(inflater, R.layout.add_new_trip_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this, factory).get(AddNewTripViewModel.class);
        // TODO: Use the ViewModel

        //FakeData for test
        MyTrip myTrip = new MyTrip();
        myTrip.setTripName("日本");
        myTrip.setStartDate(Date.valueOf("2019-1-26"));
        myTrip.setEndDate(Date.valueOf("2019-1-26"));

        mViewModel.insertNewTrip(myTrip);
        getFragmentManager().popBackStack();

    }

}
