package com.flyavis.android.ui.addnewbill;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.flyavis.android.R;
import com.flyavis.android.data.database.Bill;
import com.flyavis.android.data.database.BillDetail;
import com.flyavis.android.data.database.SimplifyPlan;
import com.flyavis.android.data.database.TeamMember;
import com.flyavis.android.databinding.AddNewBillFragmentBinding;
import com.flyavis.android.util.FlyAvisUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;

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
import timber.log.Timber;

public class AddNewBillFragment extends DaggerFragment implements ActionMode.Callback
        , AddNewBillEpoxyController.AddNewBillEpoxyControllerCallbacks {

    @Inject
    ViewModelProvider.Factory factory;
    private AddNewBillViewModel mViewModel;
    private AddNewBillFragmentBinding binding;
    private ActionMode actionMode;
    private int myTripId;
    private LiveData<List<SimplifyPlan>> simplifyPlanObservable;
    private List<SimplifyPlan> simplifyPlans;
    private LiveData<List<TeamMember>> teamMemberObservable;
    private List<TeamMember> teamMembers;
    private Bill bill;
    private List<Integer> selectedWhoJoin;
    private LocalDateTime now;
    private AddNewBillEpoxyController controller;
    private List<Integer> amounts = new ArrayList<>();
    private List<String> userId;

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
        //init category spinner
        Integer[] imageArray = {R.drawable.baseline_add_24, R.drawable.baseline_add_24,
                R.drawable.baseline_add_24, R.drawable.baseline_add_24, R.drawable.baseline_add_24
                , R.drawable.baseline_add_24};
        CategoryAdapter adapter = new CategoryAdapter(getContext(), R.layout.category_spinner
                , getResources().getStringArray(R.array.category), imageArray);
        binding.categorySpinner.setAdapter(adapter);

        binding.setSpotNameClickListener(view -> spotSelectDialog());
        binding.setTimeClickListener(view -> timePickerDialog());
        binding.setWhoJoinClickListener(view -> memberPickerDialog());

        controller = new AddNewBillEpoxyController(this);
        binding.payerEditEpoxyRecyclerView.setController(controller);

        mViewModel.setTripId(myTripId);
        simplifyPlanObservable = mViewModel.getSimplifyPlan();
        simplifyPlanObservable.observe(getViewLifecycleOwner(), simplifyPlans -> {
            this.simplifyPlans = simplifyPlans;
        });

        teamMemberObservable = mViewModel.getTeamMembers();
        teamMemberObservable.observe(getViewLifecycleOwner(), teamMembers -> {
            this.teamMembers = teamMembers;
            controller.setData(this.teamMembers);
        });

        //LocalTime backport
        now = LocalDateTime.now();
        String timeFormat = now.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
        binding.time.setText(timeFormat);
        bill = new Bill();
        bill.setCostDate(System.currentTimeMillis());

        amounts.add(0);

    }

    private void memberPickerDialog() {
        List<String> userName = new ArrayList<>();
        userId = new ArrayList<>();
        List<Boolean> selectedBoolean = new ArrayList<>();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        userName.add(auth.getCurrentUser().getDisplayName());
        userId.add(auth.getCurrentUser().getUid());
        selectedBoolean.add(false);

        if (teamMembers != null && teamMembers.size() > 0) {
            for (TeamMember teamMember : teamMembers) {
                userName.add(teamMember.getUserName());
                userId.add(teamMember.getUserId());
                selectedBoolean.add(false);
                amounts.add(0);
            }
        }
        String[] membersArray = new String[userName.size()];
        userName.toArray(membersArray);
        boolean[] selectedArray = null;

        if (selectedWhoJoin == null) {
            selectedWhoJoin = new ArrayList<>();
        } else if (selectedWhoJoin.size() > 0) {
            for (Integer integer : selectedWhoJoin) {
                selectedBoolean.set(integer, true);
            }
            selectedArray = FlyAvisUtils.toPrimitiveArray(selectedBoolean);
        }

        new MaterialAlertDialogBuilder(Objects.requireNonNull(getActivity()))
                .setTitle("選擇成員")
                .setMultiChoiceItems(membersArray, selectedArray, (dialogInterface, i, b) -> {
                    if (b) {
                        selectedWhoJoin.add(i);
                    } else if (selectedWhoJoin.contains(i)) {
                        selectedWhoJoin.remove(Integer.valueOf(i));
                    }
                })
                .setPositiveButton(getString(R.string.ok), (dialogInterface, i) -> {
                    String viewContext = "";
                    for (Integer integer : selectedWhoJoin) {
                        viewContext = viewContext.concat(userName.get(integer) + " ");
                    }
                    binding.whoJoin.setText(viewContext);
                })
                .setNegativeButton(getString(R.string.cancel), null)
                .show();
    }

    private void timePickerDialog() {
        new DatePickerDialog(Objects.requireNonNull(getContext()), (datePicker, i, i1, i2) -> {
            new TimePickerDialog(getContext(), (timePicker, j, j1) -> {
                LocalDateTime chooseDateTime = LocalDateTime.of(i, i1, i2, j, j1);
                String timeFormat = chooseDateTime
                        .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
                binding.time.setText(timeFormat);
                bill.setCostDate(chooseDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli());
            }, now.getHour(), now.getMinute(), true)
                    .show();
        }, now.getYear(), now.getMonthValue(), now.getDayOfMonth()).show();
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
                bill.setCostTitle(String.valueOf(binding.name.getText()));
                bill.setTripId(myTripId);
                mViewModel.insertNewBill(bill);
                onBillInsertSuccess();
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
        FlyAvisUtils.hideSoftKeyboard(Objects.requireNonNull(getActivity()));
        Objects.requireNonNull(getFragmentManager()).popBackStack();
    }

    @Override
    public void onStop() {
        super.onStop();
        simplifyPlanObservable.removeObservers(getViewLifecycleOwner());
        teamMemberObservable.removeObservers(getViewLifecycleOwner());
    }

    @Override
    public void onAmountTextChanged(int i, String string) {
        Timber.d(string);
        if (string == "") {
            amounts.remove(i);
        } else {
            amounts.set(i, Integer.valueOf(string));
        }
    }

    public void onBillInsertSuccess() {
        for (int join : selectedWhoJoin) {
            mViewModel.insertNewBillDetail(new BillDetail(userId.get(join), 0));
        }
        int i = 0;
        for (int j : amounts) {
            mViewModel.insertNewBillDetail(new BillDetail(userId.get(i), j));
            i++;
        }
    }
}
