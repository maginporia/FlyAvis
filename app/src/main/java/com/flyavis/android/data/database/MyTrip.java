package com.flyavis.android.data.database;

import java.sql.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity
public class MyTrip {

    @PrimaryKey(autoGenerate = true)
    private int myTripId;

    private String tripName;

    private Date startDate;

    private Date endDate;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] coverPhoto;

    private Boolean shared;


    @Ignore
    public MyTrip() {
    }

    public MyTrip(int myTripId, String tripName, Date startDate, Date endDate, byte[] coverPhoto
            , Boolean shared) {
        this.myTripId = myTripId;
        this.tripName = tripName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.coverPhoto = coverPhoto;
        this.shared = shared;
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

}
