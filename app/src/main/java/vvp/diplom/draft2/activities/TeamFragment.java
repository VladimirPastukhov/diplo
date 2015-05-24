package vvp.diplom.draft2.activities;

import android.app.Activity;
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
import android.widget.TextView;

import java.util.List;

import vvp.diplom.draft2.R;
import vvp.diplom.draft2.db.DB;
import vvp.diplom.draft2.model.Goal;
import vvp.diplom.draft2.model.Player;

/**
 * Created by VoVqa on 24.05.2015.
 */
public class TeamFragment extends Fragment {

    private static final String TAG = Util.BASE_TAG + "TeamFrg";

    private Activity A;
    private MyListAdapter myListAdapter;

    private List<Player> players;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        A = getActivity();

        String teamId = getArguments().getString(Exstras.TEAM_ID);
        String tourId = getArguments().getString(Exstras.TOURNAMENT_ID);
        Log.d(TAG, "Team id "+teamId);
        Log.d(TAG, "Tour id "+tourId);

        List<Player> players = DB.players.getByTeamIdAndTournamentId(teamId, tourId);
        Log.d(TAG, "Players " + players.toString());

        myListAdapter = new MyListAdapter<>(A, R.layout.list_row_player, players, new ViewFiller<Player>() {

            int captainPosition = 0;

            @Override
            public void fill(final int position, View view, final Player player) {
                CheckBox playerCheckbox = (CheckBox) view.findViewById(R.id.checkbox_player);
                playerCheckbox.setText(player.getName());
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_default_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView listView = (ListView) A.findViewById(R.id.list_view);
        listView.setDivider(null);
        listView.setAdapter(myListAdapter);
    }
}
