package vvp.diplom.draft2.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;

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
    private Match mMatch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String matchId = getArguments().getString(Exstras.MATCH_ID);
        mGoals = DB.goals.getByMatchId(matchId);
        Log.d(TAG, "Goals " + mGoals);

        mMatch = DB.matches.getById(matchId);
        mPlayers = DB.players.getByTournamentId(mMatch.getRound().getTournamentId());
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

        final String[] teamNames = new String[]{
                mMatch.getTeam1().getTitle(),
                mMatch.getTeam2().getTitle()};

        final String[] playerNames = new String[mPlayers.size()];
        int i = 0;
        for(Player player : mPlayers){
            playerNames[i++] = player.getName();
        }


        final ArrayAdapter<String> teamSpinnerAdapter
                = new ArrayAdapter(A, R.layout.spinner_row_text_medium, teamNames);
        teamSpinnerAdapter.setDropDownViewResource(R.layout.spinner_row_text_large);

        final ArrayAdapter<String> playerSpinnerAdapter
                = new ArrayAdapter(A, R.layout.spinner_row_text_medium, playerNames);
        playerSpinnerAdapter.setDropDownViewResource(R.layout.spinner_row_text_medium);

        ListView listView = (ListView) A.findViewById(R.id.list_view);
        myListAdapter = new MyListAdapter<>(A, R.layout.list_row_goal, mGoals, new ViewFiller<Goal>() {
            @Override
            public void fill(final int position, View view, final Goal goal) {
                Spinner teamSpinner = (Spinner) view.findViewById(R.id.spinner_team);
                Spinner playerSpinner = (Spinner) view.findViewById(R.id.spinner_player);
                CheckBox isPenaltyBox = (CheckBox) view.findViewById(R.id.checkbox_is_penalty);
                CheckBox isAutogoalBox = (CheckBox) view.findViewById(R.id.checkbox_is_autogoal);



                teamSpinner.setAdapter(teamSpinnerAdapter);
                teamSpinner.setSelection(getSelectionForTeam(goal.getTeam()));
                teamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int sPos, long id) {
                        Team team = sPos == 0 ? mMatch.getTeam1() : mMatch.getTeam2();
                        Log.d(TAG, "select team " + team + "  position " + position);
                        mGoals.get(position).setTeam(team);
                        mGoals.get(position).setTeamId(team.getId());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });


                playerSpinner.setAdapter(playerSpinnerAdapter);
                playerSpinner.setSelection(getSelectionForPlayer(goal.getPlayer()));
                playerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int sPos, long id) {
                        Player player = mPlayers.get(sPos);
                        Log.d(TAG, "select player " + player + "  position " + position);
                        mGoals.get(position).setPlayer(player);
                        mGoals.get(position).setPlayerId(player.getId());
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

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

    private int getSelectionForPlayer(Player player){
        int i = 0;
        for(Player p : mPlayers){
            if(p.getId().equals(player.getId())){
                return i;
            }
            i++;
        }
        return 0;
    }

    private int getSelectionForTeam(Team team){
        if(mMatch.getTeam2().getId().equals(team.getId()))
            return 1;
        return 0;
    }


    public void addListItem(View view){
        Team team = mMatch.getTeam1();
        Player player = mPlayers.get(0);
        Goal goal = new Goal();
        goal.setPlayer(player);
        goal.setTeam(team);
        myListAdapter.add(goal);
    }

    public void removeListItem(View view){
        int position = (Integer) view.getTag();
        myListAdapter.remove(position);
    }
}
