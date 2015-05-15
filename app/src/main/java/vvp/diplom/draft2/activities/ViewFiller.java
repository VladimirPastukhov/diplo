package vvp.diplom.draft2.activities;

import android.view.View;

/**
 * Created by VoVqa on 15.05.2015.
 */
public interface ViewFiller<T> {
    public void fill(View view, T source);
}
