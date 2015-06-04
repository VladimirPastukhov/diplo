package vvp.diplom.draft2.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.List;
import java.util.Set;

import vvp.diplom.draft2.R;
import vvp.diplom.draft2.db.DB;
import vvp.diplom.draft2.model.Player;

/**
 * Created by VoVqa on 24.05.2015.
 */
public class TeamFragment extends Fragment {

    private static final String TAG = Util.BASE_TAG + "TeamFrg";

    private View V;
    private MyListAdapter myListAdapter;

    private List<Player> mPlayers;
    private Set<String> mActivePlayers;

    private int captainPosition = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        V = inflater.inflate(R.layout.activity_default_list, container, false);
        return V;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        String teamId = getArguments().getString(Exstras.TEAM_ID);
        String matchId = getArguments().getString(Exstras.MATCH_ID);
        String tourId = getArguments().getString(Exstras.TOURNAMENT_ID);
        Log.d(TAG, "Team id "+teamId);
        Log.d(TAG, "Tour id "+tourId);

        mPlayers = DB.players.getByTeamIdAndTournamentId(teamId, tourId);
        Log.d(TAG, "Players " + mPlayers.toString());
        mActivePlayers = DB.matchPlayers.getPlayerIdsByMatchIdAndTeamId(matchId, teamId);

        myListAdapter = new MyListAdapter<>(getActivity(), R.layout.list_row_player, mPlayers, new ViewFiller<Player>() {

            @Override
            public void fill(final int position, View view, final Player player) {
                CheckBox playerCheckbox = (CheckBox) view.findViewById(R.id.checkbox_player);
                playerCheckbox.setText(player.getName());

                playerCheckbox.setChecked(isActivePlayer(player));

                RadioButton isCaptainRadio = (RadioButton) view.findViewById(R.id.radio_is_captain);
                isCaptainRadio.setChecked(position == captainPosition);
                isCaptainRadio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        captainPosition = position;
                        myListAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private boolean isActivePlayer(Player player){
        return mActivePlayers.contains(player);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated");
        super.onCreate(savedInstanceState);
        ListView listView = (ListView) V.findViewById(R.id.list_view);
        listView.setDivider(null);
        listView.setAdapter(myListAdapter);
    }
}
