package com.flyavis.android.data.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Flowable;

@Dao
public interface TeamMemberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMember(TeamMember teamMember);

    @Query("SELECT * FROM teamMember WHERE tripId In(:tripId)")
    Flowable<List<TeamMember>> getTeamMembers(int tripId);
}
