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
import vvp.diplom.draft2.db.TournamentSQLHelper;
import vvp.diplom.draft2.network.Network;
import vvp.diplom.draft2.model.Round;
import vvp.diplom.draft2.model.Tournament;

/**
 * Created by VoVqa on 13.05.2015.
 */
public class TournamentsActivity extends ActionBarActivity {

    private final String TAG = Util.BASE_TAG + "TournamentsAct";

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_list);

//        List<Tournament> tournaments = getIntent().getParcelableArrayListExtra(Exstras.TOURNAMENTS);
        List<Tournament> tournaments = DB.tournaments.getAll();

        Log.d(TAG, "Tournaments "+tournaments);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new MyListAdapter<>(this, R.layout.list_row_text_and_subtext, tournaments, new ViewFiller<Tournament>() {
            @Override
            public void fill(int position, View view, final Tournament tournament) {
                TextView textViewMain = (TextView) view.findViewById(R.id.text_view_main);
                TextView textViewSub = (TextView) view.findViewById(R.id.text_view_sub);
                textViewMain.setText(tournament.getTitle());
                String datesPattern = getText(R.string.tournament_dates_interval_pattern).toString();
                String dates = Util.formatDatesOfTournametn(tournament, datesPattern);
                textViewSub.setText(dates);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startRoundsActivity(tournament);
                    }
                });
            }
        }));
    }

    private void startRoundsActivity(Tournament tournament){
        mProgressDialog = ProgressDialog.show(this, "", "", false);
        new HttpRoundsTask().execute(tournament);
    }

    private class HttpRoundsTask extends AsyncTask<Tournament, Void, List<Round>> {
        Tournament tournament;
        @Override
        protected List<Round> doInBackground(Tournament... params) {
            try {
                tournament = params[0];
                List<Round> rounds = Network.loadRounds(tournament.getId());
                DB.rounds.insert(rounds);
                return rounds;
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
//                startRoundsActivity(tournament.getTitle(), rounds);
                startRoundsActivity(tournament.getId());
            } else {
                Util.showAlertDialog(TournamentsActivity.this,
                        R.string.network_error,
                        R.string.cant_load_rounds);
            }
        }
    }

    private void startRoundsActivity(String tournamentId){

        Intent intent = new Intent(this, RoundsActivity.class);
        intent.putExtra(Exstras.TOURNAMENT_ID, tournamentId);
        startActivity(intent);
    }

    private void startRoundsActivity(String title, List<Round> rounds){

        Intent intent = new Intent(this, RoundsActivity.class);
        intent.putExtra(Exstras.TITLE, title);
        intent.putParcelableArrayListExtra(Exstras.ROUNDS, (ArrayList) rounds);
        startActivity(intent);
    }
}
