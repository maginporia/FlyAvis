package com.flyavis.android.ui.bills;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyavis.android.R;
import com.flyavis.android.databinding.BillsFragmentBinding;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import dagger.android.support.DaggerFragment;

public class BillsFragment extends DaggerFragment implements BillsEpoxyController.BillsCallbacks {

    @Inject
    ViewModelProvider.Factory factory;
    private BillsViewModel mViewModel;
    private BillsFragmentBinding binding;
    private BillsEpoxyController controller;

    public static BillsFragment newInstance() {
        return new BillsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil  //出錯的話重新命名此layout
                .inflate(inflater, R.layout.bills_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this, factory).get(BillsViewModel.class);

        controller = new BillsEpoxyController(this);
        binding.billsRecyclerView.setController(controller);

        mViewModel.getSimplifyMyTrips().observe(getViewLifecycleOwner(), simplifyMyTrips -> {
            controller.setData(simplifyMyTrips);
        });

    }

    @Override
    public void onItemClick(int id) {
        BillsFragmentDirections.ActionBillsFragmentToBillDetailFragment action
                = BillsFragmentDirections.actionBillsFragmentToBillDetailFragment(id);
        Navigation.findNavController(Objects.requireNonNull(this.getView())).navigate(action);
    }
}
