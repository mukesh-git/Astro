package com.mukeshteckwani.astro.astroapp.utils;

import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import com.mukeshteckwani.astro.astroapp.R;
import com.mukeshteckwani.astro.astroapp.adapter.ChannelsAdapter;
import com.mukeshteckwani.astro.astroapp.model.ChannelsListModel;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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

    public static String addSecsToTime(int sec, String time, String inputFormat, String outputFormat) {
        SimpleDateFormat format1 = new SimpleDateFormat(inputFormat, Locale.US);
        SimpleDateFormat format2 = new SimpleDateFormat(outputFormat, Locale.US);
        Date date;
        try {
            date = format1.parse(time);
            long newDate = date.getTime() + sec * 1000;
            return format2.format(newDate);
        } catch (Exception e) {
            return null;
        }
    }

    private static void sort(int sortOrder, ChannelsAdapter channelsAdapter, List<ChannelsListModel.Channel> channels, Menu menu) {
        if (channels == null || channels.size() == 0)
            return;
        MenuItem sortItem = menu.findItem(R.id.sort_order);
        Handler handler = new Handler();
        switch (sortOrder) {
            case Constants.SORT_NAME_ASC:
                sortItem.setTitle("Sort Order: Name Ascending");
                new Thread(() -> {
                    Collections.sort(channels, (o1, o2) -> o1.getChannelTitle().compareTo(o2.getChannelTitle()));
                    handler.post(channelsAdapter::notifyDataSetChanged);
                }).start();
                break;

            case Constants.SORT_NAME_DESC:
                sortItem.setTitle("Sort Order: Name Descending");
                new Thread(() -> {
                    Collections.sort(channels, (o1, o2) -> o1.getChannelTitle().compareTo(o2.getChannelTitle()));
                    Collections.reverse(channels);
                    handler.post(channelsAdapter::notifyDataSetChanged);
                }).start();
                break;

            case Constants.SORT_ID_ASC:
                sortItem.setTitle("Sort Order: Channel No. Ascending");
                new Thread(() -> {
                    Collections.sort(channels, (o1, o2) -> o1.getChannelId() - o2.getChannelId());
                    handler.post(channelsAdapter::notifyDataSetChanged);
                }).start();
                break;

            case Constants.SORT_ID_DESC:
                sortItem.setTitle("Sort Order: Channel No. Descending");
                new Thread(() -> {
                    Collections.sort(channels, (o1, o2) -> o1.getChannelId() - o2.getChannelId());
                    Collections.reverse(channels);
                    handler.post(channelsAdapter::notifyDataSetChanged);
                }).start();
                break;

        }
    }
}
