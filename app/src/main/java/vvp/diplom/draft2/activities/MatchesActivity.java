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

import java.util.List;

import vvp.diplom.draft2.R;
import vvp.diplom.draft2.db.DB;
import vvp.diplom.draft2.model.Goal;
import vvp.diplom.draft2.model.Match;
import vvp.diplom.draft2.model.MatchPlayer;
import vvp.diplom.draft2.model.Round;
import vvp.diplom.draft2.model.TourPlayer;
import vvp.diplom.draft2.network.Network;

/**
 * Created by VoVqa on 15.05.2015.
 */
public class MatchesActivity extends ActionBarActivity{

    private static final String TAG = Util.BASE_TAG + "MatchesAct";

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_list);

        String roundId = getIntent().getStringExtra(Exstras.ROUND_ID);
        Round round = DB.rounds.getById(roundId);
        setTitle(Util.getRoundTitle(round));

        final List<Match> matches = DB.matches.getByRoundId(roundId);
        Log.d(TAG, "Matches "+matches.toString());

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new MyListAdapter<>(this, R.layout.list_row_text_and_subtext, matches, new ViewFiller<Match>() {
            @Override
            public void fill(int position, View view, final Match match) {
                TextView textViewMain = (TextView) view.findViewById(R.id.text_view_main);
                TextView textViewSub = (TextView) view.findViewById(R.id.text_view_sub);
                textViewMain.setText(getMatchString(match));
                textViewSub.setText(Util.formatDateAndTimeString(match.getStartAt()));
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        protocolActivity(match);
                    }
                });
            }
        }));

        ClickUtil.clickFirstListItem(this);
    }

    private static String getMatchString(Match match){
        Log.d(TAG, "  ");
        return match.getTeam1().getTitle()+" "
                +Util.scoreString(match.getGoals1(), match.getGoals2())
                +" "+match.getTeam2().getTitle();
    }

    private void protocolActivity(Match match){
        mProgressDialog = ProgressDialog.show(this, "", "", false);
        new HttpGoalsTask().execute(match.getId());
    }

    private class HttpGoalsTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String matchId = params[0];
                DB.matches.insert(Network.loadMatch(matchId));
                Match match = DB.matches.getById(matchId);
                List<Goal> goals = Network.loadGoals(match.getId());
                String team1Id = match.getTeam1().getId();
                String team2Id = match.getTeam2().getId();
                String tourId = match.getRound().getTournamentId();
                List<TourPlayer> tourPlayers1 = Network.loadTourPlayers(team1Id, tourId);
                List<TourPlayer> tourPlayers2 = Network.loadTourPlayers(team2Id, tourId);
                List<MatchPlayer> matchPlayers = Network.loadMatchPlayers(match.getId());
                DB.tourPlayers.insert(tourPlayers1);
                DB.tourPlayers.insert(tourPlayers2);
                DB.goals.insert(goals);
                DB.matchPlayers.deleteAll();
                DB.matchPlayers.insert(matchPlayers);
                return matchId;
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            finally {
                mProgressDialog.dismiss();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String matchId) {
            startProtocolActivity(matchId);
        }
    }

    private void startProtocolActivity(String matchId){
        Intent intent = new Intent(this, ProtocolActivitySwipe.class);
        intent.putExtra(Exstras.MATCH_ID, matchId);
        startActivity(intent);
    }
}
