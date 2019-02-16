package com.flyavis.android.ui.planning;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyTouchHelper;
import com.flyavis.android.R;
import com.flyavis.android.SpotItemBindingModel_;
import com.flyavis.android.data.database.Plan;
import com.flyavis.android.databinding.PlanningFragmentBinding;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayout;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import dagger.android.support.DaggerFragment;

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
    private TabLayout tabLayout;
    private String TAG = getClass().getName();
    private int day;
    private int myTripId;

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
        int totalDays = PlanningFragmentArgs
                .fromBundle(Objects.requireNonNull(getArguments())).getTotalDays();
        day = 1;
        //init recyclerView
        controller = new PlanningEpoxyController(this);
        binding.planningRecyclerView.setController(controller);
        mViewModel.getPlanningData(myTripId, day).observe
                (getViewLifecycleOwner(), plannings -> controller.setData(plannings));

        //Drag control
        EpoxyTouchHelper.initDragging(controller)
                .withRecyclerView(binding.planningRecyclerView)
                .forVerticalList()
                .withTargets(PlanningModelGroup.class, SpotItemBindingModel_.class)
                .andCallbacks(new EpoxyTouchHelper.DragCallbacks<EpoxyModel>() {
                    @Override
                    public void onModelMoved(int fromPosition, int toPosition,
                                             EpoxyModel modelBeingMoved, View itemView) {
                        //TODO add drag support
                    }
                });

        //init tab
        tabLayout = binding.tabLayout;
        for (int i = 1; i <= totalDays; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.day) + i));
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                day = tab.getPosition() + 1;
                mViewModel.getPlanningData(myTripId, day).observe
                        (PlanningFragment.this, plannings -> controller.setData(plannings));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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
    public void onTrafficTimeClick() {

    }

    private void
    navigateView(NavDirections action) {
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
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());

                Plan plan = new Plan();
                plan.setDay(day);
                plan.setPlaceId(place.getId());
                plan.setPlaceName(place.getName());
                plan.setTripId(myTripId);
                mViewModel.insetNewSpot(plan);

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

}
