package com.flyavis.android.ui.member;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyavis.android.R;
import com.flyavis.android.data.database.TeamMember;
import com.flyavis.android.databinding.MemberFragmentBinding;

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

public class MemberFragment extends DaggerFragment implements MemberEpoxyController.MemberCallbacks {

    @Inject
    ViewModelProvider.Factory factory;
    private MemberViewModel mViewModel;
    private MemberFragmentBinding binding;
    private MemberEpoxyController controller;
    private LiveData<List<TeamMember>> observable;
    private int tripId;

    public static MemberFragment newInstance() {
        return new MemberFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil
                .inflate(inflater, R.layout.member_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this, factory).get(MemberViewModel.class);
        tripId = MemberFragmentArgs.fromBundle(Objects.requireNonNull(getArguments())).getTripId();
        controller = new MemberEpoxyController(this);
        binding.membersRecyclerView.setController(controller);

        observable = mViewModel.getTeamMembers(tripId);
        observable.observe(getViewLifecycleOwner(), teamMembers -> {
            controller.setData(teamMembers);
        });
    }

    @Override
    public void onAddMemberClick(int id) {
        TeamMember teamMember = new TeamMember();
        teamMember.setUserName("MaginPoria");
        teamMember.setUserId("gsdgsdgsfgsfg");
        teamMember.setTripId(tripId);
        teamMember.setUserEmail("maginporia@gmail.com");
        mViewModel.InsertNewMember(teamMember);
    }
}
