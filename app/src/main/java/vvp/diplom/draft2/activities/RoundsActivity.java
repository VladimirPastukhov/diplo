package vvp.diplom.draft2.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vvp.diplom.draft2.R;
import vvp.diplom.draft2.network.Network;
import vvp.diplom.draft2.model.Match;
import vvp.diplom.draft2.model.Round;

/**
 * Created by VoVqa on 13.05.2015.
 */
public class RoundsActivity extends ActionBarActivity {

    private final String TAG = getClass().getSimpleName();

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_list);

        String title = getIntent().getStringExtra(Exstras.TITLE);
        setTitle(title);

        final List<Round> rounds = getIntent().getParcelableArrayListExtra(Exstras.ROUNDS);

        Log.d(TAG, "Rounds " + rounds);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new MyListAdapter<>(this, R.layout.list_row_text_and_subtext, rounds, new ViewFiller<Round>() {
            @Override
            public void fill(View view, final Round round) {
                TextView textViewMain = (TextView) view.findViewById(R.id.text_view_main);
                textViewMain.setText(round.getName());
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startMathesActivity(round);
                    }
                });
            }
        }));
    }

    private void startMathesActivity(Round round){
        mProgressDialog = ProgressDialog.show(this, "", "", false);
        new HttpMatchesTask().execute(round);
    }

    private class HttpMatchesTask extends AsyncTask<Round, Void, List<Match>> {
        Round round;
        @Override
        protected List<Match> doInBackground(Round... params) {
            try {
                round = params[0];
                return Network.loadMatches(round.getId());
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            finally {
                mProgressDialog.dismiss();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Match> matches) {
            if(matches != null){
                startMathesActivity(round.getName(), matches);
            } else {
                Util.showAlertDialog(RoundsActivity.this,
                        R.string.network_error,
                        R.string.cant_load_matches);
            }
        }
    }

    private void startMathesActivity(String roundName, List<Match> matches){
        Intent intent = new Intent(this, MatchesActivity.class);
        String title = getTitle()+"("+roundName+")";
        intent.putExtra(Exstras.TITLE, title);
        intent.putParcelableArrayListExtra(Exstras.MATCHES, (ArrayList) matches);
        startActivity(intent);
    }
}
