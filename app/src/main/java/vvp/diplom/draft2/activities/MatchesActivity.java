package vvp.diplom.draft2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import vvp.diplom.draft2.R;
import vvp.diplom.draft2.model.Match;

/**
 * Created by VoVqa on 15.05.2015.
 */
public class MatchesActivity extends ActionBarActivity{

    private static final String TAG = "MatchesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_list);

        String title = getIntent().getStringExtra(Exstras.TITLE);
        setTitle(title);

        final List<Match> matches = getIntent().getParcelableArrayListExtra(Exstras.MATCHES);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new MyListAdapter<>(this, R.layout.list_row_text_and_subtext, matches, new ViewFiller<Match>() {
            @Override
            public void fill(View view, final Match match) {
                TextView textViewMain = (TextView) view.findViewById(R.id.text_view_main);
                TextView textViewSub = (TextView) view.findViewById(R.id.text_view_sub);
                textViewMain.setText(getMatchString(match));
                textViewSub.setText(Util.formatDateAndTimeString(match.getStartAt()));
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startMatchActivity(match);
                    }
                });
            }
        }));
    }

    private static String getMatchString(Match match){
        return match.getTeam1().getTitle()+" "+match.getGoals1()+":"
                +match.getGoals2()+" "+match.getTeam2().getTitle();
    }

    private void startMatchActivity(Match match){
        Log.d(TAG, "Star tMatchActivity " + match);
        Intent intent = new Intent(this, MatchSummaryActivity.class);
        intent.putExtra(Exstras.MATCH, match);
        startActivity(intent);
    }
}
