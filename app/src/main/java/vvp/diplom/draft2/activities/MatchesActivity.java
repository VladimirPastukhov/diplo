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
import vvp.diplom.draft2.model.Goal;
import vvp.diplom.draft2.model.Match;
import vvp.diplom.draft2.model.Round;
import vvp.diplom.draft2.model.Tournament;
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
        Tournament tournament = DB.tournaments.getById(round.getTournamentId());
        setTitle(tournament.getTitle()+"("+round.getName()+")");

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
                        loadTabBarActivity(match);
                    }
                });
            }
        }));
    }

    private static String getMatchString(Match match){
        Log.d(TAG, "  ");
        return match.getTeam1().getTitle()+" "
                +Util.scoreString(match.getGoals1(), match.getGoals2())
                +" "+match.getTeam2().getTitle();
    }

    private void loadTabBarActivity(Match match){
        mProgressDialog = ProgressDialog.show(this, "", "", false);
        new HttpGoalsTask().execute(match.getId());
    }

    private class HttpGoalsTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String matchId = params[0];
                List<Goal> goals = Network.loadGoals(matchId);
                DB.goals.insert(goals);
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
        Intent intent = new Intent(this, ProtocolActivity.class);
        intent.putExtra(Exstras.MATCH_ID, matchId);
        startActivity(intent);
    }

//    private void startTabsActivity(Match match, List<Goal> goals){
//        Log.d(TAG, "Start MatchActivity " + match);
//        Intent intent = new Intent(this, ProtocolActivity.class);
//        intent.putExtra(Exstras.MATCH, match);
//        intent.putParcelableArrayListExtra(Exstras.GOALS, (ArrayList) goals);
//        startActivity(intent);
//    }
}
