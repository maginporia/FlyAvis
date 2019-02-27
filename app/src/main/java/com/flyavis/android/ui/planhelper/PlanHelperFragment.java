package com.flyavis.android.ui.planhelper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyavis.android.R;
import com.flyavis.android.databinding.PlanHelperFragmentBinding;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerFragment;

public class PlanHelperFragment extends DaggerFragment {

    @Inject
    ViewModelProvider.Factory factory;
    private PlanHelperViewModel mViewModel;
    private PlanHelperFragmentBinding binding;
    private PlanHelperEpoxyController controller;

    public static PlanHelperFragment newInstance() {
        return new PlanHelperFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil
                .inflate(inflater, R.layout.plan_helper_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this, factory).get(PlanHelperViewModel.class);

        controller = new PlanHelperEpoxyController();
        binding.planHelperRecyclerView.setController(controller);
        controller.setData(null);
        // TODO: Use the ViewModel
    }

}
