package vvp.diplom.draft2.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import vvp.diplom.draft2.R;
import vvp.diplom.draft2.model.Goal;
import vvp.diplom.draft2.model.Player;
import vvp.diplom.draft2.model.Team;

/**
 * Created by VoVqa on 20.05.2015.
 */
public class GoalsActivity extends Activity {

    private static final String TAG = "GoalsActivity";

    private MyListAdapter myListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_with_add_button);

        List<Goal> goals = getIntent().getParcelableArrayListExtra(Exstras.GOALS);
        Log.d(TAG, goals.toString());

        ListView listView = (ListView) findViewById(R.id.list_view);
        myListAdapter = new MyListAdapter<>(this, R.layout.list_row_goal, goals, new ViewFiller<Goal>() {
            @Override
            public void fill(int position, View view, final Goal goal) {
                TextView team = (TextView) view.findViewById(R.id.text_view_team);
                TextView playerName = (TextView) view.findViewById(R.id.text_view_player_name);
                CheckBox isPenaltyBox = (CheckBox) view.findViewById(R.id.checkbox_is_penalty);
                CheckBox isAutogoalBox = (CheckBox) view.findViewById(R.id.checkbox_is_autogoal);

                team.setText(goal.getTeam().getTitle());
                playerName.setText(goal.getPlayer().getName());
                isPenaltyBox.setChecked(goal.isPenalty());
                isAutogoalBox.setChecked(goal.isAutogoal());

                view.findViewById(R.id.button_remove_list_item).setTag(new Integer(position));
            }
        });
        listView.setAdapter(myListAdapter);
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
