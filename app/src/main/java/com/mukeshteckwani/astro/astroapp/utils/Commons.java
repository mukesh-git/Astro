package com.mukeshteckwani.astro.astroapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mukeshteckwani on 21/12/17.
 */

public class Commons {
    public static final String YYYY_MM_DD_HH_MM_SS_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final int DEFAULT_TIME_INTERVAL_IN_MINS = 30;

    public static String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_FORMAT, Locale.US);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String addMinsToCurrentDate(int min) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_FORMAT, Locale.US);
        Date date = new Date();
        long newDate = date.getTime() + min * 60 * 1000;
        return dateFormat.format(newDate);
    }
}
