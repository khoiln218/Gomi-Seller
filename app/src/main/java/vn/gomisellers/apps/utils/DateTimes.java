package vn.gomisellers.apps.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateTimes {

    public static String toString(Date date) {
        return toString(date, GomiConstants.SIMPLE_DATE_FORMAT);
    }

    public static String toString(long time, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT-7:00"));
        calendar.setTimeInMillis(time);
        try {
            return new SimpleDateFormat(format, new Locale("vi")).format(calendar.getTime());
        } catch (Exception e) {
            return "";
        }
    }

    public static String toString(Date date, String format) {
        try {
            return new SimpleDateFormat(format, Locale.getDefault()).format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static String combinationFormatter(final long millis) {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));
        long hours = TimeUnit.MILLISECONDS.toHours(millis);

        StringBuilder b = new StringBuilder();
        if (hours > 0) {
            b.append(hours < 10 ? String.valueOf("0" + hours) : String.valueOf(hours));
            b.append(":");
        }

        b.append(minutes == 0 ? "00" : minutes < 10 ? String.valueOf("0" + minutes) : String.valueOf(minutes));

        b.append(":");

        b.append(seconds == 0 ? "00" : seconds < 10 ? String.valueOf("0" + seconds) : String.valueOf(seconds));

        return b.toString();
    }

}
