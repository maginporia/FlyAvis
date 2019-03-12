package com.flyavis.android.data.database;

public class SimplifyMyTrip {
    private int myTripId;
    private String tripName;

    public SimplifyMyTrip(int myTripId, String tripName) {
        this.myTripId = myTripId;
        this.tripName = tripName;
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
}
