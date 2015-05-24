package vvp.diplom.draft2.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import vvp.diplom.draft2.R;
import vvp.diplom.draft2.db.DB;
import vvp.diplom.draft2.model.Goal;
import vvp.diplom.draft2.model.Match;
import vvp.diplom.draft2.model.Player;
import vvp.diplom.draft2.model.Team;

/**
 * Created by VoVqa on 22.05.2015.
 */
public class GoalsFragment extends Fragment {

    private static final String TAG = Util.BASE_TAG + "GoalsFrg";

    private Activity A;
    private MyListAdapter myListAdapter;

    private List<Goal> mGoals;
    private List<Player> mPlayers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String matchId = getArguments().getString(Exstras.MATCH_ID);
        mGoals = DB.goals.getByMatchId(matchId);
        Log.d(TAG, "Goals " + mGoals);

        Match match = DB.matches.getById(matchId);
        mPlayers = DB.players.getByTournamentId(match.getRound().getTournamentId());
        Log.d(TAG, "Players " + mPlayers);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_list_with_add_button, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        A = getActivity();

        final String[] playerNames = new String[mPlayers.size()];
        int i = 0;
        for(Player player : mPlayers){
            playerNames[i++] = player.getName();
        }

        ListView listView = (ListView) A.findViewById(R.id.list_view);
        myListAdapter = new MyListAdapter<>(A, R.layout.list_row_goal, mGoals, new ViewFiller<Goal>() {
            @Override
            public void fill(final int position, View view, final Goal goal) {
                TextView team = (TextView) view.findViewById(R.id.text_view_team);
                Spinner playerSpinner = (Spinner) view.findViewById(R.id.text_view_player_name);
                CheckBox isPenaltyBox = (CheckBox) view.findViewById(R.id.checkbox_is_penalty);
                CheckBox isAutogoalBox = (CheckBox) view.findViewById(R.id.checkbox_is_autogoal);

                team.setText(goal.getTeam().getTitle());

                ArrayAdapter<String> nameSpinnerAdapter
                        = new ArrayAdapter(A, android.R.layout.simple_spinner_item, playerNames);
                playerSpinner.setAdapter(nameSpinnerAdapter);

                isPenaltyBox.setChecked(goal.isPenalty());
                isAutogoalBox.setChecked(goal.isAutogoal());

                Button removeButton = (Button) view.findViewById(R.id.button_remove_list_item);
                removeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myListAdapter.remove(position);
                    }
                });
            }
        });
        listView.setAdapter(myListAdapter);

        final Button addButton = (Button) A.findViewById(R.id.button_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addListItem(addButton);
            }
        });
    }

    public void addListItem(View view){
        Player player = new Player();
        player.setName("Ivanov Ivan Ivanovich");
        Team team = new Team();
        team.setTitle("Spartak");

        Goal goal = new Goal();
        goal.setPlayer(player);
        goal.setTeam(team);
        goal.setIsAutogoal(true);
        goal.setIsPenalty(true);

        myListAdapter.add(goal);
    }

    public void removeListItem(View view){
        int position = (Integer) view.getTag();
        myListAdapter.remove(position);
    }
}
