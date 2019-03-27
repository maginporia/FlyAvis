package com.flyavis.android.data.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Plan.class,
        parentColumns = "planId",
        childColumns = "planId",
        onDelete = ForeignKey.CASCADE),
        indices = {@Index("planId")})
public class Bill {
    @PrimaryKey(autoGenerate = true)
    private Integer billId;
    private String costTitle;
    private int amount;
    private String currencyCode;
    private Boolean trafficCost;
    private Long costDate;
    private String category;
    private Integer planId;
    private Integer tripId;

    @Ignore
    public Bill() {
    }

    public Bill(Integer billId, String costTitle, int amount, String currencyCode
            , Boolean trafficCost, Long costDate, String category, Integer planId, Integer tripId) {
        this.billId = billId;
        this.costTitle = costTitle;
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.trafficCost = trafficCost;
        this.costDate = costDate;
        this.category = category;
        this.planId = planId;
        this.tripId = tripId;
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public String getCostTitle() {
        return costTitle;
    }

    public void setCostTitle(String costTitle) {
        this.costTitle = costTitle;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Boolean getTrafficCost() {
        return trafficCost;
    }

    public void setTrafficCost(Boolean trafficCost) {
        this.trafficCost = trafficCost;
    }

    public Long getCostDate() {
        return costDate;
    }

    public void setCostDate(Long costDate) {
        this.costDate = costDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }
}
