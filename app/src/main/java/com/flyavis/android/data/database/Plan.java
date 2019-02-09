package com.flyavis.android.data.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = MyTrip.class,
        parentColumns = "my_trip_id",
        childColumns = "trip_id"))
public class Plan {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "trip_id")
    private int tripId;

    private int day;

    private int order;

    private String placeId;

    private String placeName;

    @Ignore
    public Plan() {
    }

    public Plan(int id, int tripId, int day, int order, String placeId, String placeName) {
        this.id = id;
        this.tripId = tripId;
        this.day = day;
        this.order = order;
        this.placeId = placeId;
        this.placeName = placeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }
}
