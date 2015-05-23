package vvp.diplom.draft2;

import android.app.Application;

import vvp.diplom.draft2.db.DB;

/**
 * Created by VoVqa on 23.05.2015.
 */
public class MyApplication extends Application{
    @Override
    public void onTerminate() {
        DB.shutdown();
        super.onTerminate();
    }
}
