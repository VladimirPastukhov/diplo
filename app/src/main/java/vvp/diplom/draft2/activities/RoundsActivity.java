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
import vvp.diplom.draft2.db.DB;
import vvp.diplom.draft2.model.Tournament;
import vvp.diplom.draft2.network.Network;
import vvp.diplom.draft2.model.Match;
import vvp.diplom.draft2.model.Round;

/**
 * Created by VoVqa on 13.05.2015.
 */
public class RoundsActivity extends ActionBarActivity {

    private final String TAG = Util.BASE_TAG + "RoundsAct";

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_list);

        String tournamentId = getIntent().getStringExtra(Exstras.TOURNAMENT_ID);

        Tournament tournament = DB.tournaments.getById(tournamentId);
        setTitle(tournament.getTitle());

        List<Round> rounds = DB.rounds.getByTournamentId(tournamentId);

        Log.d(TAG, "Rounds " + rounds);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new MyListAdapter<>(this, R.layout.list_row_text_and_subtext, rounds, new ViewFiller<Round>() {
            @Override
            public void fill(int position, View view, final Round round) {
                TextView textViewMain = (TextView) view.findViewById(R.id.text_view_main);
                textViewMain.setText(round.getName());
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startMathesActivity(round);
                    }
                });
            }
        }));
    }

    private void startMathesActivity(Round round){
        mProgressDialog = ProgressDialog.show(this, "", "", false);
        new HttpMatchesTask().execute(round.getId());
    }

    private class HttpMatchesTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String roundId = params[0];
                List<Match> matches = Network.loadMatches(roundId);
                DB.matches.insert(matches);
                return roundId;
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            finally {
                mProgressDialog.dismiss();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String roundId) {
            if(roundId != null){
                startMacthesActivity(roundId);
            } else {
                Util.showAlertDialog(RoundsActivity.this,
                        R.string.network_error,
                        R.string.cant_load_matches);
            }
        }
    }

    private void startMacthesActivity(String roundId){
        Intent intent = new Intent(this, MatchesActivity.class);
        intent.putExtra(Exstras.ROUND_ID, roundId);
        startActivity(intent);
    }
}
