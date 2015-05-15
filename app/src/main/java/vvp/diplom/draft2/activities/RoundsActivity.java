package vvp.diplom.draft2.activities;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import vvp.diplom.draft2.R;
import vvp.diplom.draft2.model.Round;

/**
 * Created by VoVqa on 13.05.2015.
 */
public class RoundsActivity extends ActionBarActivity {

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_list);

        final List<Round> rounds = getIntent().getParcelableArrayListExtra(Exstras.ROUNDS);

        Log.d(TAG, "Rounds "+rounds);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new MyListAdapter<>(this, rounds, new ViewFiller<Round>() {
            @Override
            public void fill(View view, Round round) {
                TextView textViewMain = (TextView) view.findViewById(R.id.text_view_main);
                textViewMain.setText(round.getName());
            }
        }));
//        listView.setAdapter(new BaseAdapter() {
//            @Override
//            public int getCount() {
//                return rounds.size();
//            }
//
//            @Override
//            public Object getItem(int position) {
//                return null;
//            }
//
//            @Override
//            public long getItemId(int position) {
//                return 0;
//            }
//
//            class ViewHolder{
//                TextView main;
//                TextView sub;
//            }
//
//            @Override
//            public View getView(int position, View view, ViewGroup parent) {
//                ViewHolder viewHolder;
//                if(view == null){
//                    view = getLayoutInflater().inflate(R.layout.list_item_tournament, parent, false);
//                    viewHolder = new ViewHolder();
//                    viewHolder.main = (TextView) view.findViewById(R.id.text_view_main);
//                    viewHolder.sub = (TextView) view.findViewById(R.id.text_view_sub);
//                    view.setTag(viewHolder);
//                } else {
//                    viewHolder = (ViewHolder) view.getTag();
//                }
//
//                final Round round = (Round) rounds.get(position);
//                viewHolder.main.setText(round.getName());
//                return view;
//            }
//        });
    }
}
