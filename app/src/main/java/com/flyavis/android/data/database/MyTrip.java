package com.flyavis.android.data.database;

import java.sql.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "my_trip")

public class MyTrip {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "my_trip_id")
    private int myTripId;

    @ColumnInfo(name = "trip_name")
    private String tripName;

    @ColumnInfo(name = "start_date")
    private Date startDate;

    @ColumnInfo(name = "end_date")
    private Date endDate;

    @ColumnInfo(name = "cover_photo", typeAffinity = ColumnInfo.BLOB)
    private byte[] coverPhoto;

    private Boolean shared;

    @ColumnInfo(name = "who_join")
    private String whoJoin;

    @Ignore
    public MyTrip() {
    }

    public MyTrip(int myTripId, String tripName, Date startDate, Date endDate, byte[] coverPhoto
            , Boolean shared, String whoJoin) {
        this.myTripId = myTripId;
        this.tripName = tripName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.coverPhoto = coverPhoto;
        this.shared = shared;
        this.whoJoin = whoJoin;
    }

    public int getMyTripId() {
        return myTripId;
    }

    public void setMyTripId(int myTripId) {
        this.myTripId = myTripId;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public byte[] getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(byte[] coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public Boolean getShared() {
        return shared;
    }

    public void setShared(Boolean shared) {
        this.shared = shared;
    }

    public String getWhoJoin() {
        return whoJoin;
    }

    public void setWhoJoin(String whoJoin) {
        this.whoJoin = whoJoin;
    }
}
