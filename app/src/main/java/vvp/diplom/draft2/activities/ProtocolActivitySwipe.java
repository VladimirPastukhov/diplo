package vvp.diplom.draft2.activities;

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

import vvp.diplom.draft2.R;
import vvp.diplom.draft2.db.DB;
import vvp.diplom.draft2.model.Match;

/**
 * Created by VoVqa on 28.05.2015.
 */
public class ProtocolActivitySwipe extends ActionBarActivity/*FragmentActivity*/{

    private static final String TAG = Util.BASE_TAG + "ProtocolActSw";

    private Match mMatch;

    private String matchId;
    private String team1Id;
    private String team2Id;
    private String tourId;

    private Fragment mSummaryFragment;
    private Fragment mTeam1Fragment;
    private Fragment mTeam2Fragment;
    private Fragment mGoalsFragment;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_protocol_swipe);

        matchId = getIntent().getStringExtra(Exstras.MATCH_ID);
        mMatch = DB.matches.getById(matchId);
        Log.d(TAG, "Match " + mMatch);

        team1Id = mMatch.getTeam1().getId();
        team2Id = mMatch.getTeam2().getId();
        tourId  = mMatch.getRound().getTournamentId();

        mSummaryFragment = newMachSummaryFragment();
        mTeam1Fragment = newTeam1Fragment();
        mTeam2Fragment = newTeam2Fragment();
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

    private Fragment newMachSummaryFragment(){
        Fragment fragment = new MatchSummaryFragment();
        fragment.setArguments(matchBundle(mMatch));
        return fragment;
    }

    private Fragment newTeam1Fragment(){
        Fragment fragment = new TeamFragment();
        fragment.setArguments(teamMatchTourIdsBundle(team1Id, matchId, tourId));
        return fragment;
    }

    private Fragment newTeam2Fragment(){
        Fragment fragment = new TeamFragment();
        fragment.setArguments(teamMatchTourIdsBundle(team2Id, matchId, tourId));
        return fragment;
    }

    private Fragment newGoalsFragment(){
        Fragment fragment = new GoalsFragment();
        fragment.setArguments(getIntent().getExtras());
        return fragment;
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
