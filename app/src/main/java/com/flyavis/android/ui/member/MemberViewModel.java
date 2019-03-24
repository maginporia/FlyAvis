package com.flyavis.android.ui.member;

import com.flyavis.android.data.TeamMemberRepository;
import com.flyavis.android.data.database.TeamMember;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;
import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public class MemberViewModel extends ViewModel {
    private TeamMemberRepository teamMemberRepository;

    @Inject
    MemberViewModel(TeamMemberRepository teamMemberRepository) {
        this.teamMemberRepository = teamMemberRepository;
    }

    LiveData<List<TeamMember>> getTeamMembers(int tripId) {
        return LiveDataReactiveStreams.fromPublisher(teamMemberRepository.getTeamMembers(tripId));
    }

    void InsertNewMember(TeamMember teamMember) {
        Completable.fromAction(() -> teamMemberRepository.insertTeamMember(teamMember))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
