package vvp.diplom.draft2.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import vvp.diplom.draft2.R;
import vvp.diplom.draft2.model.Incident;

/**
 * Created by VoVqa on 20.05.2015.
 */
public class IncidentsActivity extends Activity {

    private static final String TAG = "IncidentsActivity";

    private MyListAdapter myListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_with_add_button);

        List<Incident> incidents = getIntent().getParcelableArrayListExtra(Exstras.INCIDENTS);
        Log.d(TAG, incidents.toString());

        ListView listView = (ListView) findViewById(R.id.list_view);
        myListAdapter = new MyListAdapter<>(this, R.layout.list_row_incident, incidents, new ViewFiller<Incident>() {
            @Override
            public void fill(int position, View view, final Incident incident) {
                EditText note = (EditText) view.findViewById(R.id.edit_text_note);
                note.setText(incident.getNote());
                view.findViewById(R.id.button_remove_list_item).setTag(new Integer(position));
            }
        });
        listView.setAdapter(myListAdapter);
        listView.setDivider(null);
    }

    public void addListItem(View view){
        Incident incident = new Incident();
        incident.setNote("Some test note");
        myListAdapter.add(incident);
    }

    public void removeListItem(View view){
        int position = (Integer) view.getTag();
        myListAdapter.remove(position);
    }
}
