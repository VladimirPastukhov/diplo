package vvp.diplom.draft2.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import vvp.diplom.draft2.R;
import vvp.diplom.draft2.model.MatchPlayer;

/**
 * Created by VoVqa on 20.05.2015.
 */
public class MatchPlayersActivity extends Activity {
    private static final String TAG = "MatchPlayersActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_list);

        List<MatchPlayer> matchPlayers = getIntent().getParcelableArrayListExtra(Exstras.MATCH_PLAYERS);
        Log.d(TAG, matchPlayers.toString());

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new MyListAdapter<>(this, R.layout.list_row_player, matchPlayers, new ViewFiller<MatchPlayer>() {
            @Override
            public void fill(View view, final MatchPlayer matchPlayer) {
                TextView nameView = (TextView) view.findViewById(R.id.text_view_player_name);
                nameView.setText(matchPlayer.getPlayer().getName());
            }
        }));

    }
}
