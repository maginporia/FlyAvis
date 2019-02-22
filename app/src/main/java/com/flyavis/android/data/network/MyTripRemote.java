package com.flyavis.android.data.network;

import java.sql.Blob;
import java.sql.Date;

public class MyTripRemote {
    private Integer tripId;
    private String tripName;
    private Date tripDate;
    private Integer tripViews;
    private Integer tripLike;
    private Integer userId;
    private Boolean tripPrivate;
    private Date createTime;
    private Blob tripPhoto;

    public MyTripRemote(Integer tripId, String tripName, Date tripDate, Integer tripViews
            , Integer tripLike, Integer userId, Boolean tripPrivate, Date createTime, Blob tripPhoto) {
        this.tripId = tripId;
        this.tripName = tripName;
        this.tripDate = tripDate;
        this.tripViews = tripViews;
        this.tripLike = tripLike;
        this.userId = userId;
        this.tripPrivate = tripPrivate;
        this.createTime = createTime;
        this.tripPhoto = tripPhoto;
    }

    public MyTripRemote() {
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public Date getTripDate() {
        return tripDate;
    }

    public void setTripDate(Date tripDate) {
        this.tripDate = tripDate;
    }

    public Integer getTripViews() {
        return tripViews;
    }

    public void setTripViews(Integer tripViews) {
        this.tripViews = tripViews;
    }

    public Integer getTripLike() {
        return tripLike;
    }

    public void setTripLike(Integer tripLike) {
        this.tripLike = tripLike;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getTripPrivate() {
        return tripPrivate;
    }

    public void setTripPrivate(Boolean tripPrivate) {
        this.tripPrivate = tripPrivate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Blob getTripPhoto() {
        return tripPhoto;
    }

    public void setTripPhoto(Blob tripPhoto) {
        this.tripPhoto = tripPhoto;
    }
}
