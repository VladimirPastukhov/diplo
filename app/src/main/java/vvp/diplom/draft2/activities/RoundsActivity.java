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

        final List<Round> rounds = getIntent().getParcelableArrayListExtra(Exstras.ROUNDS);

        Log.d(TAG, "Rounds " + rounds);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new MyListAdapter<>(this, rounds, new ViewFiller<Round>() {
            @Override
            public void fill(View view, final Round round) {
                TextView textViewMain = (TextView) view.findViewById(R.id.text_view_main);
                textViewMain.setText(round.getName());
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startMathesActivity(round.getId());
                    }
                });
            }
        }));
    }

    private void startMathesActivity(String roundId){
        mProgressDialog = ProgressDialog.show(this, "", "", false);
        new HttpMatchesTask().execute(roundId);
    }

    private class HttpMatchesTask extends AsyncTask<String, Void, List<Match>> {
        @Override
        protected List<Match> doInBackground(String... params) {
            try {
                String roundId = params[0];
                return Network.loadMatches(roundId);
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
            startMathesActivity(matches);
        }
    }

    private void startMathesActivity(List<Match> matches){
        Intent intent = new Intent(this, MatchesActivity.class);
        intent.putParcelableArrayListExtra(Exstras.MATCHES, (ArrayList) matches);
        startActivity(intent);
    }
}
