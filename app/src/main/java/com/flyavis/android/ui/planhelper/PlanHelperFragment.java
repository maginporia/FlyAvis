package com.flyavis.android.ui.planhelper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.flyavis.android.R;
import com.flyavis.android.data.database.Plan;
import com.flyavis.android.databinding.EditBudgetDialogBinding;
import com.flyavis.android.databinding.PlanHelperFragmentBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerFragment;
import timber.log.Timber;

public class PlanHelperFragment extends DaggerFragment
        implements PlanHelperEpoxyController.PlanHelperCallbacks {

    @Inject
    ViewModelProvider.Factory factory;
    private PlanHelperViewModel mViewModel;
    private PlanHelperFragmentBinding binding;
    private PlanHelperEpoxyController controller;
    private Plan observedPlan;
    private int planId;
    private LiveData<Plan> observable;

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
        Timber.d("ActivityCreated");
        mViewModel = ViewModelProviders.of(this, factory).get(PlanHelperViewModel.class);
        planId = PlanHelperFragmentArgs
                .fromBundle(Objects.requireNonNull(getArguments())).getPlanId();
        controller = new PlanHelperEpoxyController(this, this.getContext());
        binding.planHelperRecyclerView.setController(controller);

        observable = mViewModel.getPlanData(planId);
        observable.observe(getViewLifecycleOwner()
                , plan -> {
                    Timber.d("observe");
                    observedPlan = plan;
                    controller.setData(observedPlan);
                });

    }

    @Override
    public void onCheckedChange(CompoundButton button, Boolean b) {
        String spotNotice = observedPlan.getSpotNotice();
        List<String> list = null;
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

    @Override
    public void onEditClick() {
        EditBudgetDialogBinding editBudgetDialogBinding = DataBindingUtil.inflate(LayoutInflater
                .from(getContext()), R.layout.edit_budget_dialog, null, false);
        new MaterialAlertDialogBuilder(Objects.requireNonNull(getActivity()))
                .setTitle(getString(R.string.edit))
                .setView(editBudgetDialogBinding.getRoot())
                .setPositiveButton(getString(R.string.ok), (dialog, which) -> {
                    int cost = Integer.valueOf(Objects.requireNonNull(editBudgetDialogBinding
                            .costEditText.getText()).toString());
                    int traffic = Integer.valueOf(Objects.requireNonNull(editBudgetDialogBinding
                            .trafficFeeEditText.getText()).toString());
                    mViewModel.updateBudget(planId, cost, traffic);
                })
                .setNegativeButton(getString(R.string.cancel), null)
                .show();
    }

    @Override
    public void onStop() {
        super.onStop();
        observable.removeObservers(getViewLifecycleOwner());
        Timber.d("stop");
    }
}
