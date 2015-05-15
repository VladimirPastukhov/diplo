package vvp.diplom.draft2.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_list);

        String title = getIntent().getStringExtra(Exstras.TITLE);
        setTitle(title);

        final List<Match> matches = getIntent().getParcelableArrayListExtra(Exstras.MATCHES);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new MyListAdapter<>(this, matches, new ViewFiller<Match>() {
            @Override
            public void fill(View view, Match match) {
                TextView textViewMain = (TextView) view.findViewById(R.id.text_view_main);
                TextView textViewSub = (TextView) view.findViewById(R.id.text_view_sub);
                textViewMain.setText(getMatchString(match));
                textViewSub.setText(match.getStartAt());
            }
        }));


    }

    private static String getMatchString(Match match){
        return match.getTeam1().getTitle()+" "+match.getGoals1()+":"
                +match.getGoals2()+" "+match.getTeam2().getTitle();
    }
}
