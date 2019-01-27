package com.flyavis.android.ui.mytrips;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyavis.android.R;
import com.flyavis.android.data.database.MyTrip;
import com.flyavis.android.databinding.MyTripsFragmentBinding;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import dagger.android.support.DaggerFragment;

public class MyTripsFragment extends DaggerFragment {

    @Inject
    ViewModelProvider.Factory factory;
    private MyTripsViewModel mViewModel;
    private MyTripsFragmentBinding binding;
    private MyTripsEpoxyController controller;

    public static MyTripsFragment newInstance() {
        return new MyTripsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil
                .inflate(inflater, R.layout.my_trips_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this, factory)
                .get(MyTripsViewModel.class);
//        binding.setLifecycleOwner(this);

        controller = new MyTripsEpoxyController();
        binding.myTripsRecyclerView.setController(controller);
        mViewModel.getMyTripData().observe(this, new Observer<List<MyTrip>>() {
            @Override
            public void onChanged(List<MyTrip> myTrips) {
                controller.setData(mViewModel.getMyTripData().getValue());

            }
        });
        binding.floatingActionButton.setOnClickListener
                (Navigation.createNavigateOnClickListener
                        (R.id.action_myTripsFragment_to_addNewTripFragment, null));
//        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Navigation.findNavController(view).navigate(R.id.add_new_trip_fragment);
//
////                showNewTripDialog();
//            }
//        });

    }
//
//    private void showNewTripDialog() {
//        DialogFragment dialogFragment = new AddNewTripFragment();
//        dialogFragment.show(getFragmentManager(), "newTrip");
//    }

}
