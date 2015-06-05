package vvp.diplom.draft2.activities;

import android.support.v4.app.Fragment;

/**
 * Created by VoVqa on 05.06.2015.
 */
public class NamedFragment {
    private final String name;
    private final Fragment fragment;

    public NamedFragment(String name, Fragment fragment) {
        this.name = name;
        this.fragment = fragment;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public String getName() {
        return name;
    }
}
