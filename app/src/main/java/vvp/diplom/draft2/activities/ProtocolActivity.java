package vvp.diplom.draft2.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import vvp.diplom.draft2.R;
import vvp.diplom.draft2.db.DB;
import vvp.diplom.draft2.model.Match;

/**
 * Created by VoVqa on 22.05.2015.
 */
public class ProtocolActivity extends ActionBarActivity{

    private static final String TAG = Util.BASE_TAG + "ProtocolAct";

    private GoalsFragment mGoalsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol);

        String matchId = getIntent().getStringExtra(Exstras.MATCH_ID);
        Match match = DB.matches.getById(matchId);
        Log.d(TAG, "Match " + match);

        setTitle(Util.getRoundTitle(match.getRound()));

        FragmentTabHost tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        String team1Id= match.getTeam1().getId();
        String team2Id= match.getTeam2().getId();
        String tourId = match.getRound().getTournamentId();

        String summary = getString(R.string.protocol_tab_summary);
        tabHost.addTab(tabHost.newTabSpec(summary).setIndicator(summary),
                MatchSummaryFragment.class, matchBundle(match));

        String teams1 = getString(R.string.protocol_tab_team1);
        tabHost.addTab(tabHost.newTabSpec(teams1).setIndicator(teams1),
                TeamFragment.class, teamMatchTourIdsBundle(team1Id, matchId, tourId));

        String teams2 = getString(R.string.protocol_tab_team2);
        tabHost.addTab(tabHost.newTabSpec(teams2).setIndicator(teams2),
                TeamFragment.class, teamMatchTourIdsBundle(team2Id, matchId, tourId));

        String goals = getString(R.string.protocol_tab_goals);
        tabHost.addTab(tabHost.newTabSpec(goals).setIndicator(goals),
                GoalsFragment.class, getIntent().getExtras());
    }

    private static Bundle matchBundle(Match match){
        Bundle bundle = new Bundle();
        bundle.putParcelable(Exstras.MATCH, match);
        return bundle;
    }

    private static Bundle teamMatchTourIdsBundle(String teamId, String mathcId, String tourId){
        Bundle bundle = new Bundle();
        bundle.putString(Exstras.TEAM_ID, teamId);
        bundle.putString(Exstras.MATCH_ID, mathcId);
        bundle.putString(Exstras.TOURNAMENT_ID, tourId);
        return bundle;
    }
}
