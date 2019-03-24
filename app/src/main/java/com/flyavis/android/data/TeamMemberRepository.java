package com.flyavis.android.data;

import com.flyavis.android.data.database.TeamMember;
import com.flyavis.android.data.database.TeamMemberDao;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class TeamMemberRepository {
    private final TeamMemberDao teamMemberDao;

    @Inject
    TeamMemberRepository(TeamMemberDao teamMemberDao) {
        this.teamMemberDao = teamMemberDao;
    }

    public void insertTeamMember(TeamMember teamMember) {
        teamMemberDao.insertMember(teamMember);
    }

    public Flowable<List<TeamMember>> getTeamMembers(int tripId) {
        return teamMemberDao.getTeamMembers(tripId);
    }
}
