package com.flyavis.android.ui.billdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyavis.android.R;
import com.flyavis.android.data.database.Bill;
import com.flyavis.android.databinding.BillDetailFragmentBinding;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import dagger.android.support.DaggerFragment;

public class BillDetailFragment extends DaggerFragment {

    @Inject
    ViewModelProvider.Factory factory;
    private BillDetailViewModel mViewModel;
    private BillDetailFragmentBinding binding;
    private BillDetailEpoxyController controller;
    private int myTripId;
    private LiveData<List<Bill>> observable;

    public static BillDetailFragment newInstance() {
        return new BillDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil  //出錯的話重新命名此layout
                .inflate(inflater, R.layout.bill_detail_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this, factory).get(BillDetailViewModel.class);
        myTripId = BillDetailFragmentArgs
                .fromBundle(Objects.requireNonNull(getArguments())).getMyTyipId();
        controller = new BillDetailEpoxyController();
        binding.billDetailRecyclerView.setController(controller);

        observable = mViewModel.getBills(0);
        observable.observe(getViewLifecycleOwner(), bills -> {
            controller.setData(bills);
        });

        binding.floatingActionButton.setOnClickListener(view -> {
            BillDetailFragmentDirections.ActionBillDetailFragmentToAddNewBillFragment action
                    = BillDetailFragmentDirections.actionBillDetailFragmentToAddNewBillFragment(myTripId);
            Navigation.findNavController(Objects.requireNonNull(this.getView())).navigate(action);
        });
    }

}
