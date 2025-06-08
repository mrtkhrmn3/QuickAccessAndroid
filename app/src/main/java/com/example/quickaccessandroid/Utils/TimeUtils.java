package com.example.quickaccessandroid.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimeUtils {

    public static String getTimeAgo(String dateStr) {
        try {
            // Tarih string: 2025-06-07T23:26:50
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            Date past = sdf.parse(dateStr);
            Date now = new Date();

            long diffMillis = now.getTime() - past.getTime();

            long seconds = TimeUnit.MILLISECONDS.toSeconds(diffMillis);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis);
            long hours = TimeUnit.MILLISECONDS.toHours(diffMillis);
            long days = TimeUnit.MILLISECONDS.toDays(diffMillis);

            if (seconds < 60) {
                return "just now";
            } else if (minutes < 60) {
                return minutes + " mins ago";
            } else if (hours < 24) {
                return hours + " hours ago";
            } else {
                return days + " days ago";
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return "unknown time";
        }
    }
}
