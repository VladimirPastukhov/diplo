package vvp.diplom.draft2.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import vvp.diplom.draft2.R;
import vvp.diplom.draft2.model.Match;

/**
 * Created by VoVqa on 22.05.2015.
 */
public class MatchSummaryFragment extends Fragment {

    private static final String TAG = Util.BASE_TAG + "MatchSummaryFr";

    private Match mMatch;

    private Activity A;
    private ProgressDialog mProgressDialog;
    private Button mDateButton;
    private Button mTimeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_match_summary, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        A = getActivity();

//        mMatch = A.getIntent().getParcelableExtra(Exstras.MATCH);
        mMatch = getArguments().getParcelable(Exstras.MATCH);

        TextView team1Title = (TextView) A.findViewById(R.id.text_view_title_team_1);
        TextView team2Title = (TextView) A.findViewById(R.id.text_view_title_team_2);
        team1Title.setText(mMatch.getTeam1().getTitle());
        team2Title.setText(mMatch.getTeam2().getTitle());

        TextView goalsScore = (TextView) A.findViewById(R.id.text_view_goals_score);
        goalsScore.setText(Util.scoreString(mMatch.getGoals1(), mMatch.getGoals2()));

        TextView penaltyScore = (TextView) A.findViewById(R.id.text_view_penalties_score);
        penaltyScore.setText(Util.scoreString(mMatch.getPenalty1(), mMatch.getPenalty2()));

        CheckBox isOvertimeBox = (CheckBox) A.findViewById(R.id.checkbox_is_overtime);
        isOvertimeBox.setChecked(mMatch.isOvertime());

        CheckBox isTechnicalWinBox = (CheckBox) A.findViewById(R.id.checkbox_is_technical_win);
        isTechnicalWinBox.setChecked(mMatch.isTechnical());

        EditText referee = (EditText) A.findViewById(R.id.edit_text_match_judge);
        referee.setText(mMatch.getReferee());

        EditText place = (EditText) A.findViewById(R.id.edit_text_match_location);
        place.setText(mMatch.getPlace());

        mDateButton = (Button) A.findViewById(R.id.button_match_date);
        mDateButton.setText(Util.dateString(mMatch.getStartAt()));
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickDialog();
            }
        });

        mTimeButton = (Button) A.findViewById(R.id.button_match_time);
        mTimeButton.setText(Util.timeString(mMatch.getStartAt()));
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickDialog();
            }
        });
    }

    public void showDatePickDialog(){
        Calendar calendar = Util.calendarFromString(mMatch.getStartAt());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(A, new MyOnDateSetListener(), year, month, day).show();
    }

    private class MyOnDateSetListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String dateString = Util.dateString(year, monthOfYear, dayOfMonth);
            mDateButton.setText(dateString);
        }
    }

    public void showTimePickDialog(){
        Calendar calendar = Util.calendarFromString(mMatch.getStartAt());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        new TimePickerDialog(A, new MyOnTimeSetListener(), hour, minute, true).show();
    }

    private class MyOnTimeSetListener implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String timeString = Util.timeString(hourOfDay, minute);
            mTimeButton.setText(timeString);
        }
    }

//    private class HttpMatchPlayersTask extends AsyncTask<String, Void, List<MatchPlayer>> {
//        @Override
//        protected List<MatchPlayer> doInBackground(String... params) {
//            try {
//                String matchId = params[0];
//                return Network.loadMatchPlayers(matchId);
//            } catch (Exception e) {
//                Log.e(TAG, e.getMessage(), e);
//            }
//            finally {
//                mProgressDialog.dismiss();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(List<MatchPlayer> matchPlayers) {
//            startMatchPlayersActivity(matchPlayers);
//        }
//    }
//
//    private void startMatchPlayersActivity(List<MatchPlayer> matchPlayers){
//        Intent intent = new Intent(A, MatchPlayersActivity.class);
//        intent.putParcelableArrayListExtra(Exstras.MATCH_PLAYERS, (ArrayList) matchPlayers);
//        startActivity(intent);
//    }
//
//    private class HttpIncidentsTask extends AsyncTask<String, Void, List<Incident>> {
//        @Override
//        protected List<Incident> doInBackground(String... params) {
//            try {
//                String matchId = params[0];
//                return Network.loadIncidents(matchId);
//            } catch (Exception e) {
//                Log.e(TAG, e.getMessage(), e);
//            }
//            finally {
//                mProgressDialog.dismiss();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(List<Incident> incidents) {
//            startIncidentsActivity(incidents);
//        }
//    }
//
//    private void startIncidentsActivity(List<Incident> incidents){
//        Intent intent = new Intent(A, IncidentsActivity.class);
//        intent.putParcelableArrayListExtra(Exstras.INCIDENTS, (ArrayList) incidents);
//        startActivity(intent);
//    }
}
