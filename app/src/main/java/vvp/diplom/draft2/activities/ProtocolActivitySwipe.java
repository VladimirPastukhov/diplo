package vvp.diplom.draft2.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import vvp.diplom.draft2.R;
import vvp.diplom.draft2.db.DB;
import vvp.diplom.draft2.model.Match;
import vvp.diplom.draft2.model.MatchPlayer;
import vvp.diplom.draft2.model.Protocol;
import vvp.diplom.draft2.network.Network;

/**
 * Created by VoVqa on 28.05.2015.
 */
public class ProtocolActivitySwipe extends ActionBarActivity/*FragmentActivity*/{

    private static final String TAG = Util.BASE_TAG + "ProtocolActSw";

    private Match mMatch;
    private List<MatchPlayer> mMatchPlayers1;
    private List<MatchPlayer> mMatchPlayers2;

    private String matchId;
    private String team1Id;
    private String team2Id;
    private String tourId;

    private MatchSummaryFragment mSummaryFragment;
    private Fragment mTeam1Fragment;
    private Fragment mTeam2Fragment;
    private Fragment mGoalsFragment;

    private ViewPager mViewPager;
    private Button mSendButton;

    private ProgressDialog mProgressDialog;

    private Protocol mProtocol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_protocol_swipe);

        mProtocol = new Protocol();

        matchId = getIntent().getStringExtra(Exstras.MATCH_ID);
        mMatch = DB.matches.getById(matchId);
        Log.d(TAG, "Match " + mMatch);

        mProtocol.setMatch(mMatch);

        team1Id = mMatch.getTeam1().getId();
        team2Id = mMatch.getTeam2().getId();
        tourId  = mMatch.getRound().getTournamentId();


        mMatchPlayers1 = DB.matchPlayers.getExtendedList(tourId, matchId, team1Id);
        mMatchPlayers2 = DB.matchPlayers.getExtendedList(tourId, matchId, team2Id);

        mSummaryFragment = MatchSummaryFragment.create(mMatch);
        mTeam1Fragment = TeamFragment.create(mMatchPlayers1);
        mTeam2Fragment = TeamFragment.create(mMatchPlayers2);
        mGoalsFragment = newGoalsFragment();

        setTitle(Util.getRoundTitle(mMatch.getRound()));

        mViewPager = (ViewPager) findViewById(R.id.pager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        PagerAdapter pagerAdapter = new MyPagerAdapter(fragmentManager);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener(){
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                mViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {}
            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {}
        };

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        mViewPager.setAdapter(pagerAdapter);
        actionBar.addTab(actionBar.newTab().setText(R.string.protocol_tab_summary).setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText(R.string.protocol_tab_team1).setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText(R.string.protocol_tab_team2).setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText(R.string.protocol_tab_goals).setTabListener(tabListener));

        mSendButton = (Button) findViewById(R.id.button_send);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSummaryFragment.updateModel();
                mProgressDialog = ProgressDialog.show(ProtocolActivitySwipe.this, "", "", false);
                new HttpPatchMatchTask().execute(mProtocol.getMatch());
            }
        });
    }

    private class HttpPatchMatchTask extends AsyncTask<Match, Void, Void> {
        @Override
        protected Void doInBackground(Match... params) {
            try {
                Match match = params[0];
                Network.patchMatch(match);
                Network.deleteMatchPlayers(matchId);
                Network.postMatchPlayers(getActualMatchPlayers());
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            finally {
                mProgressDialog.dismiss();
            }
            return null;
        }
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter{
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.d(TAG, "get page item for position "+position);
            switch (position){
                case 0: return mSummaryFragment;
                case 1: return mTeam1Fragment;
                case 2: return mTeam2Fragment;
                case 3: return mGoalsFragment;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public int getCount() {
            return 4;
        }
    }


    private Fragment newGoalsFragment(){
        Fragment fragment = new GoalsFragment();
        fragment.setArguments(getIntent().getExtras());
        return fragment;
    }

    private List<MatchPlayer> getActualMatchPlayers(){
        List<MatchPlayer> list = new ArrayList<>();
        for(MatchPlayer matchPlayer : mMatchPlayers1){
            if(matchPlayer.isActive())
                list.add(matchPlayer);
        }
        for(MatchPlayer matchPlayer : mMatchPlayers2){
            if(matchPlayer.isActive())
                list.add(matchPlayer);
        }
        return list;
    }
}
