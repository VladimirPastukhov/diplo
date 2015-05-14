package vvp.diplom.draft2.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import vvp.diplom.draft2.R;
import vvp.diplom.draft2.controller.Network;
import vvp.diplom.draft2.model.Round;

/**
 * Created by VoVqa on 13.05.2015.
 */
public class RoundsActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_list);

//        Parcelable[] rounds = getIntent().getParcelableArrayExtra(Exstras.ROUNDS);
//
//        String[] names = new String[rounds.length];
//        for(int i = 0; i < rounds.length; i++){
//            names[i] = ((Round) rounds[i]).getName();
//        }
        String tournamentId = getIntent().getExtras().getString(Exstras.TOURNAMENT_ID);
        new HttpRoundsTask().execute(tournamentId);

//        ListView listView = (ListView) findViewById(R.id.list_view);
//        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item_text, names));
    }

    private class HttpRoundsTask extends AsyncTask<String, Void, Round[]> {
        @Override
        protected Round[] doInBackground(String... params) {
            try {
                String tournametnId = params[0];
                return Network.loadRounds(tournametnId);
            } catch (Exception e) {
//                logLoginException(e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Round[] rounds) {
            fillListView(rounds);
        }
    };

    private void fillListView(Round[] rounds){
        String[] names = new String[rounds.length];
        for(int i = 0; i < rounds.length; i++) {
            names[i] = rounds[i].getName();
        }
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.text_view_medium, names));
    }
}
