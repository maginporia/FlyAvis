package com.flyavis.android.data.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = MyTrip.class,
        parentColumns = "myTripId",
        childColumns = "tripId",
        onDelete = ForeignKey.CASCADE),
        indices = {@Index("tripId")})
public class TeamMember {

    @PrimaryKey(autoGenerate = true)
    private Integer memberId;
    private Integer userId;
    private String memberName;
    private String teamName;
    private Integer tripId;

    @Ignore
    public TeamMember() {
    }

    public TeamMember(Integer memberId, Integer userId, String memberName, String teamName, Integer tripId) {
        this.memberId = memberId;
        this.userId = userId;
        this.memberName = memberName;
        this.teamName = teamName;
        this.tripId = tripId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }
}
