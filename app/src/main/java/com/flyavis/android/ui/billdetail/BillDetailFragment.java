package com.flyavis.android.ui.billdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyavis.android.R;
import com.flyavis.android.databinding.BillDetailFragmentBinding;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerFragment;

public class BillDetailFragment extends DaggerFragment {

    @Inject
    ViewModelProvider.Factory factory;
    private BillDetailViewModel mViewModel;
    private BillDetailFragmentBinding binding;

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
        // TODO: Use the ViewModel
    }

}
