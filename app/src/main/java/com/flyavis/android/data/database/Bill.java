package com.flyavis.android.data.database;

import java.sql.Timestamp;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Plan.class,
        parentColumns = "planId",
        childColumns = "planId",
        onDelete = ForeignKey.CASCADE))
public class Bill {
    @PrimaryKey(autoGenerate = true)
    private Integer costId;
    private String costTitle;
    private Integer memberId;
    private Boolean ifTrafficCost;
    private Timestamp costDate;
    private String category;
    private Integer planId;
    private Integer singlePayer;
    private Integer singleCost;

    public Bill(Integer costId, String costTitle, Integer memberId, Boolean ifTrafficCost
            , Timestamp costDate, String category, Integer planId, Integer singlePayer
            , Integer singleCost) {
        this.costId = costId;
        this.costTitle = costTitle;
        this.memberId = memberId;
        this.ifTrafficCost = ifTrafficCost;
        this.costDate = costDate;
        this.category = category;
        this.planId = planId;
        this.singlePayer = singlePayer;
        this.singleCost = singleCost;
    }

    @Ignore
    public Bill() {
    }

    public Integer getCostId() {
        return costId;
    }

    public void setCostId(Integer costId) {
        this.costId = costId;
    }

    public String getCostTitle() {
        return costTitle;
    }

    public void setCostTitle(String costTitle) {
        this.costTitle = costTitle;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Boolean getIfTrafficCost() {
        return ifTrafficCost;
    }

    public void setIfTrafficCost(Boolean ifTrafficCost) {
        this.ifTrafficCost = ifTrafficCost;
    }

    public Timestamp getCostDate() {
        return costDate;
    }

    public void setCostDate(Timestamp costDate) {
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

    public Integer getSinglePayer() {
        return singlePayer;
    }

    public void setSinglePayer(Integer singlePayer) {
        this.singlePayer = singlePayer;
    }

    public Integer getSingleCost() {
        return singleCost;
    }

    public void setSingleCost(Integer singleCost) {
        this.singleCost = singleCost;
    }
}
