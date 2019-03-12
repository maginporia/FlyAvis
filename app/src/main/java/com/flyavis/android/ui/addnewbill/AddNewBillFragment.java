package com.flyavis.android.ui.addnewbill;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.flyavis.android.R;
import com.flyavis.android.data.database.Bill;
import com.flyavis.android.data.database.SimplifyPlan;
import com.flyavis.android.databinding.AddNewBillFragmentBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerFragment;

public class AddNewBillFragment extends DaggerFragment implements ActionMode.Callback {

    @Inject
    ViewModelProvider.Factory factory;
    private AddNewBillViewModel mViewModel;
    private AddNewBillFragmentBinding binding;
    private ActionMode actionMode;
    private int myTripId;
    private LiveData<List<SimplifyPlan>> simplifyPlanObservable;
    private List<SimplifyPlan> simplifyPlans;
    private Bill bill;

    public static AddNewBillFragment newInstance() {
        return new AddNewBillFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil
                .inflate(inflater, R.layout.add_new_bill_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this, factory).get(AddNewBillViewModel.class);
        myTripId = AddNewBillFragmentArgs
                .fromBundle(Objects.requireNonNull(getArguments())).getMyTripId();
        if (actionMode == null) {
            actionMode = ((AppCompatActivity) Objects.requireNonNull(getActivity()))
                    .startSupportActionMode(this);
        }

        Integer[] imageArray = {R.drawable.baseline_add_24, R.drawable.baseline_add_24,
                R.drawable.baseline_add_24, R.drawable.baseline_add_24, R.drawable.baseline_add_24
                , R.drawable.baseline_add_24};
        CategoryAdapter adapter = new CategoryAdapter(getContext(), R.layout.category_spinner
                , getResources().getStringArray(R.array.category), imageArray);

        binding.categorySpinner.setAdapter(adapter);

        binding.setSpotNameClickListener(view -> spotSelectDialog());
        binding.setTimeClickListener(view -> timePickerDialog());

        simplifyPlanObservable = mViewModel.getSimPlifyPlan(myTripId);
        simplifyPlanObservable.observe(getViewLifecycleOwner(), simplifyPlans -> {
            this.simplifyPlans = simplifyPlans;
        });
    }

    private void timePickerDialog() {
        new TimePickerDialog(getContext(), (timePicker, i, i1) -> {

        }, 0, 0, true)
                .show();
    }

    private void spotSelectDialog() {

        List<String> days = new ArrayList<>();
        if (simplifyPlans != null && simplifyPlans.size() > 0) {
            int planDay = simplifyPlans.get(simplifyPlans.size() - 1).getPlanDay();
            for (int i = 0; i < planDay; i++) {
                days.add("Day" + planDay);
            }
        }
        String[] daysArray = new String[days.size()];
        days.toArray(daysArray);

        new MaterialAlertDialogBuilder(Objects.requireNonNull(getActivity()))
                .setTitle("選擇天數")
                .setItems(daysArray, (dialogInterface, i) -> {
                    List<String> spotNames = new ArrayList<>();
                    List<Integer> planIdList = new ArrayList<>();
                    for (SimplifyPlan plan : simplifyPlans) {
                        if (plan.getPlanDay() == i + 1) {
                            spotNames.add(plan.getSpotName());
                            planIdList.add(plan.getPlanId());
                        }
                    }
                    String[] spotNamesArray = new String[spotNames.size()];
                    spotNames.toArray(spotNamesArray);

                    new MaterialAlertDialogBuilder(Objects.requireNonNull(getActivity()))
                            .setTitle("選擇景點")
                            .setItems(spotNamesArray, (dialogInterface1, i1) -> {
                                binding.spotNameEditText.setText(spotNamesArray[i1]);
                                bill.setPlanId(planIdList.get(i1));
                            })

                            .show();
                })
                .show();
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:

                mode.finish();
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        actionMode = null;
        hideSoftKeyboard(Objects.requireNonNull(getActivity()));
        Objects.requireNonNull(getFragmentManager()).popBackStack();
    }

    private void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() == null) {
            return;
        }
        InputMethodManager inputMethodManager
                = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(inputMethodManager)
                .hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onStop() {
        super.onStop();
        simplifyPlanObservable.removeObservers(getViewLifecycleOwner());
    }
}
