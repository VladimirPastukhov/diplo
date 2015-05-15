package vvp.diplom.draft2.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vvp.diplom.draft2.R;
import vvp.diplom.draft2.network.Network;
import vvp.diplom.draft2.model.Round;
import vvp.diplom.draft2.model.Tournament;

/**
 * Created by VoVqa on 13.05.2015.
 */
public class TournamentsActivity extends ActionBarActivity {

    private final String TAG = getClass().getSimpleName();

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_list);

        List<Tournament> tournaments = getIntent().getParcelableArrayListExtra(Exstras.TOURNAMENTS);

        Log.d(TAG, "Tournametns "+tournaments);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new MyListAdapter<>(this, tournaments, new ViewFiller<Tournament>() {
            @Override
            public void fill(View view, final Tournament tournament) {
                TextView textViewMain = (TextView) view.findViewById(R.id.text_view_main);
                TextView textViewSub = (TextView) view.findViewById(R.id.text_view_sub);
                textViewMain.setText(tournament.getTitle());
                textViewSub.setText(tournament.getStartDate() + " " + tournament.getEndDate());
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startRoundsActivity(tournament.getId());
                    }
                });

            }
        }));
    }

    private void startRoundsActivity(String tournamentId){
        mProgressDialog = ProgressDialog.show(this, "", "", false);
        new HttpRoundsTask().execute(tournamentId);
    }

    private class HttpRoundsTask extends AsyncTask<String, Void, List<Round>> {
        @Override
        protected List<Round> doInBackground(String... params) {
            try {
                String tournamentId = params[0];
                return Network.loadRounds(tournamentId);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            finally {
                mProgressDialog.dismiss();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Round> rounds) {
            if(rounds != null){
                startRoundsActivity(rounds);
            } else {
                Util.showAlertDialog(TournamentsActivity.this,
                        R.string.network_error,
                        R.string.cant_load_matches);
            }
        }
    }

    private void startRoundsActivity(List<Round> rounds){
        Intent intent = new Intent(this, RoundsActivity.class);
        intent.putParcelableArrayListExtra(Exstras.ROUNDS, (ArrayList) rounds);
        startActivity(intent);
    }
}
