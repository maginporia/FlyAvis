package com.flyavis.android.ui.planhelper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.flyavis.android.R;
import com.flyavis.android.data.database.Plan;
import com.flyavis.android.databinding.PlanHelperFragmentBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerFragment;

public class PlanHelperFragment extends DaggerFragment
        implements PlanHelperEpoxyController.PlanHelperCallbacks {

    @Inject
    ViewModelProvider.Factory factory;
    private PlanHelperViewModel mViewModel;
    private PlanHelperFragmentBinding binding;
    private PlanHelperEpoxyController controller;
    private Plan plan;
    private int planId;

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
        planId = PlanHelperFragmentArgs
                .fromBundle(Objects.requireNonNull(getArguments())).getPlanId();
        controller = new PlanHelperEpoxyController(this, this.getContext());
        binding.planHelperRecyclerView.setController(controller);

        mViewModel.getPlanData(planId).observe(getViewLifecycleOwner()
                , plan -> {
                    this.plan = plan;
                    controller.setData(plan);
                });

    }

    @Override
    public void onCheckedChange(CompoundButton button, Boolean b) {
        String spotNotice = plan.getSpotNotice();
        List<String> list;
        if (spotNotice != null) {
            list = new ArrayList<>(Arrays.asList(spotNotice.split(", ")));
            if (b) {
                list.add((String) button.getText());
            } else {
                list.remove(button.getText());
            }
        } else {
            list = new ArrayList<>();
            list.add((String) button.getText());
        }
        String newString = "";
        for (String s : list) {
            if (newString.equals("")) {
                newString = s;
            } else {
                newString = newString + ", " + s;
            }

        }
        mViewModel.updateNotice(planId, newString);
    }
}
