package com.flyavis.android.ui.billdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyavis.android.R;
import com.flyavis.android.data.database.Bill;
import com.flyavis.android.data.database.BillDetail;
import com.flyavis.android.data.database.TeamMember;
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
    private LiveData<List<Bill>> billObservable;
    private LiveData<List<BillDetail>> billDetailObservable;
    private LiveData<List<TeamMember>> teamMemberObservable;

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

        billObservable = mViewModel.getBills(myTripId);
        billObservable.observe(getViewLifecycleOwner(), bills -> {
            billDetailObservable = mViewModel.getBillDetails(myTripId);
            billDetailObservable.observe(getViewLifecycleOwner(), billDetails -> {
                teamMemberObservable = mViewModel.getTeamMembers(myTripId);
                teamMemberObservable.observe(getViewLifecycleOwner(), teamMembers -> {
                    controller.setData(bills, billDetails, teamMembers);
                    billObservable.removeObservers(getViewLifecycleOwner());
                    billDetailObservable.removeObservers(getViewLifecycleOwner());
                    teamMemberObservable.removeObservers(getViewLifecycleOwner());
                });
            });
        });

        binding.floatingActionButton.setOnClickListener(view -> {
            BillDetailFragmentDirections.ActionBillDetailFragmentToAddNewBillFragment action
                    = BillDetailFragmentDirections.actionBillDetailFragmentToAddNewBillFragment(myTripId);
            Navigation.findNavController(Objects.requireNonNull(this.getView())).navigate(action);
        });
    }

}
