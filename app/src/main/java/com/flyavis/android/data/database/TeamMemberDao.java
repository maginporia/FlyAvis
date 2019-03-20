package com.flyavis.android.data.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Flowable;

@Dao
public interface TeamMemberDao {

    @Insert
    void insertMember(TeamMember teamMember);

    @Query("SELECT * FROM teamMember")
    Flowable<List<TeamMember>> getTeamMembers();
}
