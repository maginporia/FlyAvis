package com.flyavis.android.data.database;

import java.sql.Time;

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
public class Plan {

    @PrimaryKey(autoGenerate = true)
    private int planId;

    private int tripId;

    private int planDay;

    private int spotOrder;

    private Double longitude;

    private Double latitude;

    private String placeId;

    private String spotName;

    private Time spotStartTime;

    private Time spotEndTime;

    private Integer spotCost;

    private String spotNotice;

    private Integer spotTrafficFee;

    private Boolean planB;

    @Ignore
    public Plan() {
    }

    public Plan(int planId, int tripId, int planDay, int spotOrder, Double longitude
            , Double latitude, String placeId, String spotName, Time spotStartTime, Time spotEndTime
            , Integer spotCost, String spotNotice, Integer spotTrafficFee, Boolean planB) {
        this.planId = planId;
        this.tripId = tripId;
        this.planDay = planDay;
        this.spotOrder = spotOrder;
        this.longitude = longitude;
        this.latitude = latitude;
        this.placeId = placeId;
        this.spotName = spotName;
        this.spotStartTime = spotStartTime;
        this.spotEndTime = spotEndTime;
        this.spotCost = spotCost;
        this.spotNotice = spotNotice;
        this.spotTrafficFee = spotTrafficFee;
        this.planB = planB;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getPlanDay() {
        return planDay;
    }

    public void setPlanDay(int planDay) {
        this.planDay = planDay;
    }

    public int getSpotOrder() {
        return spotOrder;
    }

    public void setSpotOrder(int spotOrder) {
        this.spotOrder = spotOrder;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public Time getSpotStartTime() {
        return spotStartTime;
    }

    public void setSpotStartTime(Time spotStartTime) {
        this.spotStartTime = spotStartTime;
    }

    public Time getSpotEndTime() {
        return spotEndTime;
    }

    public void setSpotEndTime(Time spotEndTime) {
        this.spotEndTime = spotEndTime;
    }

    public Integer getSpotCost() {
        return spotCost;
    }

    public void setSpotCost(Integer spotCost) {
        this.spotCost = spotCost;
    }

    public String getSpotNotice() {
        return spotNotice;
    }

    public void setSpotNotice(String spotNotice) {
        this.spotNotice = spotNotice;
    }

    public Integer getSpotTrafficFee() {
        return spotTrafficFee;
    }

    public void setSpotTrafficFee(Integer spotTrafficFee) {
        this.spotTrafficFee = spotTrafficFee;
    }

    public Boolean getPlanB() {
        return planB;
    }

    public void setPlanB(Boolean planB) {
        this.planB = planB;
    }
}
