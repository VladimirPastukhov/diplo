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

import vvp.diplom.draft2.R;
import vvp.diplom.draft2.model.MatchPlayer;

/**
 * Created by VoVqa on 05.06.2015.
 */
public class TeamFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = Util.BASE_TAG + "TeamFrg";

    private View V;
    private MyListAdapter myListAdapter;

    private List<MatchPlayer> mMatchPlayers;

    private int captainPosition = 0;

    public static TeamFragment create(List<MatchPlayer> matchPlayers){
        TeamFragment teamFragment = new TeamFragment();
        teamFragment.mMatchPlayers = matchPlayers;
        return teamFragment;
    }

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

        myListAdapter = new MyListAdapter<>(getActivity(), R.layout.list_row_player, mMatchPlayers, new ViewFiller<MatchPlayer>() {
            @Override
            public void fill(final int position, View view, final MatchPlayer matchPlayer) {
                CheckBox playerCheckbox = (CheckBox) view.findViewById(R.id.checkbox_player);
                Log.d(TAG, "position " + position + ", checkbox " + playerCheckbox + ", matchPlayer " + matchPlayer);
                playerCheckbox.setText(matchPlayer.getPlayer().getName());
                playerCheckbox.setChecked(matchPlayer.isActive());
                playerCheckbox.setTag(matchPlayer);
                playerCheckbox.setOnClickListener(TeamFragment.this);

                CheckBox isGoalKeeperCheckBox = (CheckBox) view.findViewById(R.id.checkbox_is_goalkeeper);
                isGoalKeeperCheckBox.setChecked(matchPlayer.isGoalkeeper());
                isGoalKeeperCheckBox.setTag(matchPlayer);
                isGoalKeeperCheckBox.setOnClickListener(TeamFragment.this);

                RadioButton isCaptainRadio = (RadioButton) view.findViewById(R.id.radio_is_captain);
                isCaptainRadio.setChecked(matchPlayer.isCaptain());
                isCaptainRadio.setTag(matchPlayer);
                isCaptainRadio.setOnClickListener(TeamFragment.this);
            }
        });


    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "on click "+v);
        MatchPlayer matchPlayer = (MatchPlayer) v.getTag();
        switch (v.getId()){
            case(R.id.checkbox_player):
                matchPlayer.setStatusSimple(((CheckBox) v).isChecked());
                break;
            case(R.id.checkbox_is_goalkeeper):
                matchPlayer.setIsGoalkeeper(((CheckBox) v).isChecked());
                break;
            case(R.id.radio_is_captain):
                for (MatchPlayer mp : mMatchPlayers) {
                    mp.setIsCaptain(false);
                }
                matchPlayer.setIsCaptain(true);
                myListAdapter.notifyDataSetChanged();
                break;
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated");
        super.onCreate(savedInstanceState);
        ListView listView = (ListView) V.findViewById(R.id.list_view);
        listView.setDivider(null);
        listView.setAdapter(myListAdapter);
//        listView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }
}
