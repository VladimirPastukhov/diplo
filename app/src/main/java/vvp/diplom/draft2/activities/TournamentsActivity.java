package vvp.diplom.draft2.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import vvp.diplom.draft2.R;
import vvp.diplom.draft2.controller.Network;
import vvp.diplom.draft2.model.Round;
import vvp.diplom.draft2.model.Tournament;

/**
 * Created by VoVqa on 13.05.2015.
 */
public class TournamentsActivity extends ActionBarActivity {

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_list);

        final Parcelable[] tournaments =  getIntent().getParcelableArrayExtra(Exstras.TOURNAMENTS);

        for(Parcelable tournament : tournaments){
            Log.i(getClass().getSimpleName(), "TOURNAMETN "+tournament);
        }

        ListView listView = (ListView) findViewById(R.id.list_view);

        ArrayAdapter<Parcelable> adapter = new ArrayAdapter<Parcelable>(this, R.layout.list_item_tournament, tournaments){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
//                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View rowView = getLayoutInflater().inflate(R.layout.list_item_tournament, parent, false);

                TextView name = (TextView) rowView.findViewById(R.id.text_view_tournament_name);
                TextView dates = (TextView) rowView.findViewById(R.id.text_view_tournament_dates);
                final Tournament tournament = (Tournament) tournaments[position];
                name.setText(tournament.getTitle());
                dates.setText(tournament.getStartDate()+" "+tournament.getEndDate());
                rowView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startRoundsActivity(tournament.getId());
                    }
                });
                return rowView;
            }
        };

        listView.setAdapter(adapter);
    }

    private void startRoundsActivity(String tournamentId){
        Intent intent = new Intent(this, RoundsActivity.class);
        intent.putExtra(Exstras.TOURNAMENT_ID, tournamentId);
        startActivity(intent);
    }

//    private void startRoundsActivity(String tournamentId){
//        progressDialog = ProgressDialog.show(this, "", "", false);
//        new HttpRoundsTask().execute(tournamentId);
//    }

    private class HttpRoundsTask extends AsyncTask<String, Void, Round[]> {
        @Override
        protected Round[] doInBackground(String... params) {
            try {
                String tournamentId = params[0];
                return Network.loadRounds(tournamentId);
            } catch (Exception e) {
//                logLoginException(e);
                Log.i("AAAA","AAAA");
            }
            finally {
                progressDialog.dismiss();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Round[] rounds) {
            startRoundsActivity(rounds);
        }
    }

    private void startRoundsActivity(Round[] rounds){
        Intent intent = new Intent(this, RoundsActivity.class);
        intent.putExtra(Exstras.ROUNDS, rounds);
        startActivity(intent);
    }
}
