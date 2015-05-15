package vvp.diplom.draft2.activities;

import android.app.Activity;
import android.app.AlertDialog;

import vvp.diplom.draft2.R;

/**
 * Created by VoVqa on 15.05.2015.
 */
public class Util {
    public static void showAlertDialog(Activity activity, int titleStringId, int messageStringId){
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(titleStringId);
        alert.setMessage(messageStringId);
        alert.setPositiveButton(R.string.button_ok_alert_dialog,null);
        alert.show();
    }
}
