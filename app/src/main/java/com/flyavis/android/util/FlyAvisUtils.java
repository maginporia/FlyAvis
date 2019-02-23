package com.flyavis.android.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FlyAvisUtils {
    public static Date StringToDate(String string, String pattern, Locale locale) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
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
        return (int) ((StringToDate(split[1], "yyyy-MM-dd", Locale.US).getTime()
                - StringToDate(split[0], "yyyy-MM-dd", Locale.US).getTime())
                / (24 * 60 * 60 * 1000));
    }

    public static long StringToTimeLong(String string) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);
        long ms = 0;
        try {
            ms = sdf.parse(string).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ms;
    }
}
