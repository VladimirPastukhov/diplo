package vvp.diplom.draft2.activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ListView;

import java.util.List;

import vvp.diplom.draft2.R;
import vvp.diplom.draft2.db.DB;
import vvp.diplom.draft2.model.Round;
import vvp.diplom.draft2.network.Network;

/**
 * Created by VoVqa on 04.06.2015.
 */
public class ClickUtil {
    public static void clickFillAndEnter(Activity a){
        a.findViewById(R.id.button_login_fill).performClick();
        a.findViewById(R.id.button_login_enter).performClick();
    }

    public static void clickFirstListItem(Activity a) {
        new FirstListItemClickTask().execute(a);
    }

    private static class FirstListItemClickTask extends AsyncTask<Activity, Void, Activity> {
        @Override
        protected Activity doInBackground(Activity... args) {
            try {
                Thread.sleep(300);
                return args[0];
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Activity activity) {
            ListView listView = (ListView) activity.findViewById(R.id.list_view);
            MyListAdapter adapter = (MyListAdapter) listView.getAdapter();
            adapter.getFirstView().performClick();
        }
    }
}
