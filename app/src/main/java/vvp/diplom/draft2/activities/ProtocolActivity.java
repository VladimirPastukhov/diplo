package vvp.diplom.draft2.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import vvp.diplom.draft2.R;
import vvp.diplom.draft2.db.DB;
import vvp.diplom.draft2.model.Match;
import vvp.diplom.draft2.model.Round;
import vvp.diplom.draft2.model.Tournament;

/**
 * Created by VoVqa on 22.05.2015.
 */
public class ProtocolActivity extends ActionBarActivity{

    private static final String TAG = Util.BASE_TAG + "TabBarAct";

    private GoalsFragment mGoalsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol);

        String matchId = getIntent().getStringExtra(Exstras.MATCH_ID);
        Match match = DB.matches.getById(matchId);

        Round round = DB.rounds.getById(match.getRoundId());
        Tournament tournament = DB.tournaments.getById(round.getTournamentId());
        setTitle(tournament.getTitle()+"("+round.getName()+")");

        FragmentTabHost tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        Bundle bundle = new Bundle();
        bundle.putParcelable(Exstras.MATCH, match);
        String summary = getString(R.string.protocol_tab_summary);
        tabHost.addTab(tabHost.newTabSpec(summary)
                        .setIndicator(summary), MatchSummaryFragment.class, bundle);

//        String goals = getString(R.string.protocol_tab_goals);
//        tabHost.addTab(tabHost.newTabSpec(goals).setIndicator("goals"),
//                GoalsFragment.class, getIntent().getExtras());
//
//
//        String teams = getString(R.string.protocol_tab_teams);
//        tabHost.addTab(tabHost.newTabSpec(teams).setIndicator(teams),
//                MatchSummaryFragment.class, getIntent().getExtras());
//
//        String cards = getString(R.string.protocol_tab_cards);
//        tabHost.addTab(tabHost.newTabSpec(cards).setIndicator(cards),
//                MatchSummaryFragment.class, getIntent().getExtras());
    }
}
