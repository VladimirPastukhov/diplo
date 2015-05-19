package vvp.diplom.draft2.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import vvp.diplom.draft2.R;

/**
 * Created by VoVqa on 15.05.2015.
 */
public class Util {

    private static final String TAG = "Util";

    public static void showAlertDialog(Activity activity, int titleStringId, int messageStringId){
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(titleStringId);
        alert.setMessage(messageStringId);
        alert.setPositiveButton(R.string.button_ok_alert_dialog,null);
        alert.show();
    }

    public static Calendar dateFromString(String dateString){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return null;
    }

    public static String dateString(int year, int monthOfYear, int dayOfMonth){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String timeString(int hourOfDay, int minute){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static final String scoreString(String goals1, String goals2){
        if(goals1 == null || goals1.equals("null")) {
            goals1 = "0";
        }
        if(goals2 == null || goals2.equals("null")) {
            goals2 = "0";
        }
        return String.format("%s : %s", goals1, goals2);
    }
}
