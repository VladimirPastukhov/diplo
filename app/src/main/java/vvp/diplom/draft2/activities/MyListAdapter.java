package vvp.diplom.draft2.activities;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import vvp.diplom.draft2.R;

/**
 * Created by VoVqa on 15.05.2015.
 */
public class MyListAdapter<T> extends BaseAdapter {

    private final Activity activity;
    private final int resource;
    private final List<T> items;
    private final ViewFiller<T> viewFiller;

    public MyListAdapter(Activity activity, int resource, List<T> items, ViewFiller<T> viewFiller) {
        this.activity = activity;
        this.resource = resource;
        this.items = items;
        this.viewFiller = viewFiller;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(resource, parent, false);
        viewFiller.fill(view, items.get(position));
        return view;
    }
}
