package com.flyavis.android.data.database;

public class SimplifyPlan {
    private int planId;
    private int planDay;
    private String spotName;

    public SimplifyPlan(int planId, int planDay, String spotName) {
        this.planId = planId;
        this.planDay = planDay;
        this.spotName = spotName;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public int getPlanDay() {
        return planDay;
    }

    public void setPlanDay(int planDay) {
        this.planDay = planDay;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }
}
