package com.flyavis.android.ui.planning;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.epoxy.EpoxyTouchHelper;
import com.flyavis.android.R;
import com.flyavis.android.data.database.Plan;
import com.flyavis.android.databinding.PlanBottomSheetBinding;
import com.flyavis.android.databinding.PlanningFragmentBinding;
import com.flyavis.android.util.FlyAvisUtils;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayout;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import dagger.android.support.DaggerFragment;
import timber.log.Timber;

import static android.animation.ValueAnimator.ofObject;
import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.flyavis.android.Constants.AUTOCOMPLETE_REQUEST_CODE;

public class PlanningFragment extends DaggerFragment implements PlanningEpoxyController.PlanningCallbacks {

    @Inject
    ViewModelProvider.Factory factory;
    @Inject
    Context context;
    private PlanningViewModel mViewModel;
    private PlanningFragmentBinding binding;
    private PlanningEpoxyController controller;
    private int day;
    private Time nextSpotTime;
    private Date firstDate;
    private SimpleDateFormat sdFormat;
    private int totalDays;
    private int myTripId;
    private List<Plan> planList;

    public static PlanningFragment newInstance() {
        return new PlanningFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil//出錯的話重新命名此layout
                .inflate(inflater, R.layout.planning_fragment, container, false);
        return binding.getRoot();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this, factory).get(PlanningViewModel.class);

        //取得上一個畫面傳的參數
        myTripId = PlanningFragmentArgs
                .fromBundle(Objects.requireNonNull(getArguments())).getMyTripId();
        String dateRange = PlanningFragmentArgs
                .fromBundle(Objects.requireNonNull(getArguments())).getDateRange();
        //date handle
        String[] split = dateRange.split(" ~ ");
        firstDate = FlyAvisUtils
                .StringToDate(split[0], "yyyy-MM-dd", Locale.US);
        sdFormat = new SimpleDateFormat("yyyy.MM.dd E", Locale.TAIWAN);
        sdFormat.format(firstDate);
        totalDays = FlyAvisUtils.calculateDays(dateRange) + 1;
        //init some values
        day = 1;
        nextSpotTime = Time.valueOf("08:00:00");

        initEpoxyRecyclerView();
        initTab();
    }

    private void initTab() {
        TabLayout tabLayout = binding.tabLayout;
        for (int i = 1; i <= totalDays; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.day) + i));
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                day = tab.getPosition() + 1;
                mViewModel.getPlanningData(myTripId, day).observe
                        (PlanningFragment.this, listResource -> {
                            planList = listResource.data;
                            long l = firstDate.getTime() + (day - 1) * 24 * 60 * 60 * 1000;
                            Date newDate = new Date(l);
                            controller.setData(planList, sdFormat.format(newDate));

                            if (planList != null && planList.size() > 0) {
                                nextSpotTime = planList.get(planList.size() - 1).getSpotEndTime();
                            } else {
                                nextSpotTime = Time.valueOf("08:00:00");
                            }
                            Timber.d("plan observed");
                        });

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initEpoxyRecyclerView() {
        controller = new PlanningEpoxyController(this);
        binding.planningRecyclerView.setController(controller);
        mViewModel.getPlanningData(myTripId, day).observe
                (getViewLifecycleOwner(), listResource -> {
                    planList = listResource.data;
                    controller.setData(planList, sdFormat.format(firstDate));
                    if (planList != null && planList.size() != 0) {
                        nextSpotTime = planList.get(planList.size() - 1).getSpotEndTime();
                    }
                    Timber.d("plan observed");
                });

        //drag support
        EpoxyTouchHelper.initDragging(controller)
                .withRecyclerView(binding.planningRecyclerView)
                .forVerticalList()
                .withTarget(PlanningModelGroup.class)
                .andCallbacks(new EpoxyTouchHelper.DragCallbacks<PlanningModelGroup>() {
                    @ColorInt
                    final int selectedBackgroundColor = Color.argb(200, 200, 200, 200);
                    ValueAnimator backgroundAnimator = null;

                    @Override
                    public void onModelMoved(int fromPosition, int toPosition,
                                             PlanningModelGroup modelBeingMoved, View itemView) {
                        Timber.d("position:" + fromPosition + ">" + toPosition);
                        Timber.d(String.valueOf("plan size:" + planList.size()));
                        planList.add(fromPosition - 1 + (toPosition - fromPosition)
                                , planList.remove(fromPosition - 1));
                        for (int i = 0; i < planList.size(); i++) {
                            Plan plan = planList.get(i);
                            plan.setSpotOrder(i);
                            planList.set(i, plan);
                        }
                    }

                    @Override
                    public void onDragStarted(PlanningModelGroup model, View itemView, int adapterPosition) {
                        backgroundAnimator = ofObject(new ArgbEvaluator(), Color.WHITE, selectedBackgroundColor);
                        backgroundAnimator.addUpdateListener(
                                animator -> itemView.setBackgroundColor((int) animator.getAnimatedValue())
                        );
                        backgroundAnimator.start();
                        itemView
                                .animate()
                                .scaleX(1.05f)
                                .scaleY(1.05f);
                    }

                    @Override
                    public void onDragReleased(PlanningModelGroup model, View itemView) {
                        if (backgroundAnimator != null) {
                            backgroundAnimator.cancel();
                        }
                        backgroundAnimator =
                                ofObject(new ArgbEvaluator()
                                        , ((ColorDrawable) itemView.getBackground()).getColor(),
                                        Color.WHITE);
                        backgroundAnimator.addUpdateListener(
                                animator -> itemView.setBackgroundColor((int) animator.getAnimatedValue())
                        );
                        backgroundAnimator.start();
                        itemView
                                .animate()
                                .scaleX(1f)
                                .scaleY(1f);

                    }

                    @Override
                    public void clearView(PlanningModelGroup model, View itemView) {
                        onDragReleased(model, itemView);
                        mViewModel.updatePlanOrder(planList);
                    }
                });
    }

    @Override
    public void onAddNewSpotViewClick() {
        new MaterialAlertDialogBuilder(Objects.requireNonNull(getActivity()))
                .setTitle(getString(R.string.add_a_new_spot))
                .setItems(R.array.add_new_spot, (dialogInterface, i) -> {
                    switch (i) {
                        case 0:
                            placeApiSearch();
                            break;
                        case 1:
                            break;
                        default:
                            break;
                    }
                })
                .show();
    }

    @Override
    public void onSpotViewClick() {

    }

    @Override
    public void onMoreButtonClick(Plan plan) {
        //show bottomSheet
        BottomSheetDialog bottomSheetDialog
                = new BottomSheetDialog(Objects.requireNonNull(getContext()));
        PlanBottomSheetBinding bottomSheetBinding
                = DataBindingUtil.inflate(LayoutInflater.from(getContext())
                , R.layout.plan_bottom_sheet, null, false);
        bottomSheetDialog.setContentView(bottomSheetBinding.getRoot());
        bottomSheetDialog.show();

        bottomSheetBinding.setEditStayTimeClickListener(view -> {
            new TimePickerDialog(getContext(), (timePicker, i, i1) -> {
                long l = plan.getSpotStartTime().getTime() + i1 * 1000 * 60 + i * 1000 * 60 * 60;
                Time time = new Time(l);
                plan.setSpotEndTime(time);
                mViewModel.updatePlan(plan);
                bottomSheetDialog.dismiss();
            }, 0, 0, true).show();

        });

        bottomSheetBinding.setDeleteClickListener(view -> {
                    mViewModel.deletePlan(plan);
                    bottomSheetDialog.dismiss();
                }
        );
    }

    @Override
    public void onTrafficTimeClick() {

    }

    private void navigateView(NavDirections action) {
        Navigation.findNavController(Objects.requireNonNull(this.getView())).navigate(action);
    }

    private void placeApiSearch() {
        if (!Places.isInitialized()) {
            Places.initialize(context, getString(R.string.google_maps_key));
        }
        // Set the fields to specify which types of place data to return.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                .build(context);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Timber.i("Place: " + place.getName() + ", " + place.getId());

                Plan plan = new Plan();
                plan.setPlanDay(day);
                plan.setPlaceId(place.getId());
                plan.setSpotName(place.getName());
                plan.setTripId(myTripId);
                plan.setSpotStartTime(nextSpotTime);
                long l = nextSpotTime.getTime() + (60 * 60 * 1000);
                Time time = new Time(l);
                plan.setSpotEndTime(time);
                plan.setSpotOrder(999);
                mViewModel.insetNewSpot(plan);

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Timber.i(status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}
