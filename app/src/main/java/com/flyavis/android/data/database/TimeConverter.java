package com.flyavis.android.data.database;

import java.sql.Time;

import androidx.room.TypeConverter;

public class TimeConverter {
    @TypeConverter
    public static Time toTime(Long timestamp) {
        return timestamp == null ? null : new Time(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Time time) {
        return time == null ? null : time.getTime();
    }
}
