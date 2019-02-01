package com.flyavis.android.ui.mytrips;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.flyavis.android.R;
import com.flyavis.android.databinding.MyTripsFragmentBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import dagger.android.support.DaggerFragment;

public class MyTripsFragment extends DaggerFragment
        implements MyTripsEpoxyController.MyTripsCallbacks, ActionMode.Callback {

    @Inject
    ViewModelProvider.Factory factory;
    private MyTripsViewModel mViewModel;
    private MyTripsFragmentBinding binding;
    private MyTripsEpoxyController controller;
    private ActionMode actionMode;
    private Set<Integer> positionSet = new HashSet<>();
    private FloatingActionButton floatingActionButton;

    public static MyTripsFragment newInstance() {
        return new MyTripsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil//出錯的話重新命名此layout
                .inflate(inflater, R.layout.my_trips_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this, factory)
                .get(MyTripsViewModel.class);
//        binding.setLifecycleOwner(this);

        controller = new MyTripsEpoxyController(this);
        binding.myTripsRecyclerView.setController(controller);
        mViewModel.getMyTripData().observe
                (this, myTrips -> controller.setData(mViewModel.getMyTripData().getValue()));
        floatingActionButton = binding.floatingActionButton;
        floatingActionButton.setOnClickListener
                (Navigation.createNavigateOnClickListener
                        (R.id.action_myTripsFragment_to_addNewTripFragment, null));
    }

    @Override
    public void onMyTripItemClick
            (MyTripsFragmentDirections.ActionMyTripsFragmentToPlanningFragment action) {
        Navigation.findNavController(Objects.requireNonNull(this.getView())).navigate(action);
    }

    @Override
    public void onMyTripItemLongClick(int id, boolean deleteState, int clickedCount) {

        if (actionMode == null) {
            actionMode = ((AppCompatActivity) Objects.requireNonNull(getActivity()))
                    .startSupportActionMode(this);
            floatingActionButton.setVisibility(View.INVISIBLE);
        }
        if (positionSet.contains(id)) {
            positionSet.remove(id);
        } else {
            positionSet.add(id);
        }
        if (!deleteState) actionMode.finish();
    }


    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.delete_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                mViewModel.deleteMyTrip(positionSet);
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
        floatingActionButton.setVisibility(View.VISIBLE);
        mViewModel.refresh();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (actionMode != null) actionMode.finish();
    }
}
