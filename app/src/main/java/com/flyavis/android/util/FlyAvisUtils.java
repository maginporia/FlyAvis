package com.flyavis.android.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FlyAvisUtils {
    public static Date StringToDate(String string) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date date = null;
        try {
            date = sdf.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static int calculateDays(String string) {
        String[] split = string.split(" ~ ");
        return (int) ((StringToDate(split[1]).getTime() - StringToDate(split[0]).getTime())
                / (24 * 60 * 60 * 1000));
    }
}
