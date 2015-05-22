package vvp.diplom.draft2.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vvp.diplom.draft2.R;
import vvp.diplom.draft2.model.Goal;
import vvp.diplom.draft2.model.Incident;
import vvp.diplom.draft2.model.Match;
import vvp.diplom.draft2.model.MatchPlayer;
import vvp.diplom.draft2.network.Network;

/**
 * Created by VoVqa on 19.05.2015.
 */
public class MatchSummaryActivity extends Activity {

    private static final String TAG = "MatchSummaryActivity";

    private Match mMatch;

    private ProgressDialog mProgressDialog;
//    private Match mEditableMatch;

    private Button mDateButton;
    private Button mTimeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_summary);

        mMatch = getIntent().getParcelableExtra(Exstras.MATCH);
//        mEditableMatch = new Match(mMatch);

        TextView team1Title = (TextView) findViewById(R.id.text_view_title_team_1);
        TextView team2Title = (TextView) findViewById(R.id.text_view_title_team_2);
        team1Title.setText(mMatch.getTeam1().getTitle());
        team2Title.setText(mMatch.getTeam2().getTitle());

        TextView goalsScore = (TextView) findViewById(R.id.text_view_goals_score);
        goalsScore.setText(Util.scoreString(mMatch.getGoals1(), mMatch.getGoals2()));

        TextView penaltyScore = (TextView) findViewById(R.id.text_view_penalties_score);
        penaltyScore.setText(Util.scoreString(mMatch.getPenalty1(), mMatch.getPenalty2()));

        CheckBox isOvertimeBox = (CheckBox) findViewById(R.id.checkbox_is_overtime);
        isOvertimeBox.setChecked(mMatch.isOvertime());

        CheckBox isTechnicalWinBox = (CheckBox) findViewById(R.id.checkbox_is_technical_win);
        isTechnicalWinBox.setChecked(mMatch.isTechnical());

        EditText referee = (EditText) findViewById(R.id.edit_text_match_judge);
        referee.setText(mMatch.getReferee());

        EditText place = (EditText) findViewById(R.id.edit_text_match_location);
        place.setText(mMatch.getPlace());

        mDateButton = (Button) findViewById(R.id.button_match_date);
        mTimeButton = (Button) findViewById(R.id.button_match_time);
        mDateButton.setText(Util.dateString(mMatch.getStartAt()));
        mTimeButton.setText(Util.timeString(mMatch.getStartAt()));
    }

    public void showDatePickDialog(View view){
        Calendar calendar = Util.calendarFromString(mMatch.getStartAt());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(this, new MyOnDateSetListener(), year, month, day).show();
    }

    private class MyOnDateSetListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String dateString = Util.dateString(year, monthOfYear, dayOfMonth);
            mDateButton.setText(dateString);
        }
    }

    public void showTimePickDialog(View view){
        Calendar calendar = Util.calendarFromString(mMatch.getStartAt());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        new TimePickerDialog(this, new MyOnTimeSetListener(), hour, minute, true).show();
    }

    private class MyOnTimeSetListener implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String timeString = Util.timeString(hourOfDay, minute);
            mTimeButton.setText(timeString);
        }
    }

    public void openPlayers(View view){
        mProgressDialog = ProgressDialog.show(this, "", "", false);
//        new HttpMatchPlayersTask().execute(mMatch.getId());
        new HttpGoalsTask().execute(mMatch.getId());
//        new HttpIncidentsTask().execute(mMatch.getId());
    }

    private class HttpMatchPlayersTask extends AsyncTask<String, Void, List<MatchPlayer>> {
        @Override
        protected List<MatchPlayer> doInBackground(String... params) {
            try {
                String matchId = params[0];
                return Network.loadMatchPlayers(matchId);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            finally {
                mProgressDialog.dismiss();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<MatchPlayer> matchPlayers) {
            startMatchPlayersActivity(matchPlayers);
        }
    }

    private void startMatchPlayersActivity(List<MatchPlayer> matchPlayers){
        Intent intent = new Intent(this, MatchPlayersActivity.class);
        intent.putParcelableArrayListExtra(Exstras.MATCH_PLAYERS, (ArrayList) matchPlayers);
        startActivity(intent);
    }

    private class HttpGoalsTask extends AsyncTask<String, Void, List<Goal>> {
        @Override
        protected List<Goal> doInBackground(String... params) {
            try {
                String matchId = params[0];
                return Network.loadGoals(matchId);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            finally {
                mProgressDialog.dismiss();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Goal> goals) {
            startGoalsActivity(goals);
        }
    }

    private void startGoalsActivity(List<Goal> goals){
        Intent intent = new Intent(this, GoalsActivity.class);
        intent.putParcelableArrayListExtra(Exstras.GOALS, (ArrayList) goals);
        startActivity(intent);
    }

    private class HttpIncidentsTask extends AsyncTask<String, Void, List<Incident>> {
        @Override
        protected List<Incident> doInBackground(String... params) {
            try {
                String matchId = params[0];
                return Network.loadIncidents(matchId);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            finally {
                mProgressDialog.dismiss();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Incident> incidents) {
            startIncidentsActivity(incidents);
        }
    }

    private void startIncidentsActivity(List<Incident> incidents){
        Intent intent = new Intent(this, IncidentsActivity.class);
        intent.putParcelableArrayListExtra(Exstras.INCIDENTS, (ArrayList) incidents);
        startActivity(intent);
    }
}
