package vvp.diplom.draft2.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import vvp.diplom.draft2.R;
import vvp.diplom.draft2.model.Tournament;

/**
 * Created by VoVqa on 15.05.2015.
 */
public class Util {

    private static final String TAG = "Util";

    private static final String INPUT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String INPUT_DATE_TIME_FORMAT_TOURNAMENT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final String OUTPUT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String OUTPUT_TIME_FORMAT = "HH:mm";
    private static final String OUTPUT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    public static void showAlertDialog(Activity activity, int titleStringId, int messageStringId){
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(titleStringId);
        alert.setMessage(messageStringId);
        alert.setPositiveButton(R.string.button_ok_alert_dialog,null);
        alert.show();
    }

    public static Calendar calendarFromString(String dateAndTimeString){
        return calendarFromString(dateAndTimeString, INPUT_DATE_TIME_FORMAT);
    }

    public static Calendar calendarFromString(String dateAndTimeString, String pattern){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            Date date = simpleDateFormat.parse(dateAndTimeString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return null;
    }

    public static String dateString(int year, int monthOfYear, int dayOfMonth){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        return dateString(calendar);
    }

    public static String timeString(int hourOfDay, int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        return timeString(calendar);
    }

    public static String dateString(String dateAndTimeString){
        Calendar calendar = calendarFromString(dateAndTimeString);
        return dateString(calendar);
    }

    public static String timeString(String dateAndTimeString){
        Calendar calendar = calendarFromString(dateAndTimeString);
        return timeString(calendar);
    }

    private static String dateString(Calendar calendar){
        return new SimpleDateFormat(OUTPUT_DATE_FORMAT).format(calendar.getTime());
    }

    private static String timeString(Calendar calendar){
        return new SimpleDateFormat(OUTPUT_TIME_FORMAT).format(calendar.getTime());
    }

    public static String formatDateAndTimeString(String dateAndTimeString){
        Calendar calendar = calendarFromString(dateAndTimeString);
        return new SimpleDateFormat(OUTPUT_DATE_TIME_FORMAT).format(calendar.getTime());
    }

    public static String formatDatesOfTournametn(Tournament tournament, String outputPattern){
        return formatDatesOfTournametn(tournament.getStartDate(), tournament.getEndDate(), outputPattern);
    }

    private static String formatDatesOfTournametn(String date1, String date2, String outputPattern){
        String pattern = INPUT_DATE_TIME_FORMAT_TOURNAMENT;
        Calendar calendar1 = calendarFromString(date1, pattern);
        Calendar calendar2 = calendarFromString(date2, pattern);
        return String.format(outputPattern, dateString(calendar1), dateString(calendar2));
    }


    public static String scoreString(String goals1, String goals2){
        if(goals1 == null || goals1.equals("null")) {
            goals1 = "0";
        }
        if(goals2 == null || goals2.equals("null")) {
            goals2 = "0";
        }
        return String.format("%s : %s", goals1, goals2);
    }
}
