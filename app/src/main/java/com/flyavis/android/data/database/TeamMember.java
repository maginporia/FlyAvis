package com.flyavis.android.data.database;

import android.net.Uri;

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
    private String userId;
    private String userName;
    private String userEmail;
    private Uri userPhoto;
    private String teamName;
    private Integer tripId;

    @Ignore
    public TeamMember() {
    }

    public TeamMember(Integer memberId, String userId, String userName, String userEmail
            , Uri userPhoto, String teamName, Integer tripId) {
        this.memberId = memberId;
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhoto = userPhoto;
        this.teamName = teamName;
        this.tripId = tripId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Uri getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(Uri userPhoto) {
        this.userPhoto = userPhoto;
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
