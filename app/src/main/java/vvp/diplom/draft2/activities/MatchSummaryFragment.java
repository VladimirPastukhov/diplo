package vvp.diplom.draft2.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import vvp.diplom.draft2.network.Network;

/**
 * Created by VoVqa on 22.05.2015.
 */
public class MatchSummaryFragment extends Fragment {

    private static final String TAG = Util.BASE_TAG + "MatchSummaryFr";

    private View V;
    private Match mMatch;
    private ProgressDialog mProgressDialog;
    private Button mDateButton;
    private Button mTimeButton;

    private Button mSendButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMatch = getArguments().getParcelable(Exstras.MATCH);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        V = inflater.inflate(R.layout.fragment_match_summary, container, false);
        return V;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView team1Title = (TextView) V.findViewById(R.id.text_view_title_team_1);
        TextView team2Title = (TextView) V.findViewById(R.id.text_view_title_team_2);
        team1Title.setText(mMatch.getTeam1().getTitle());
        team2Title.setText(mMatch.getTeam2().getTitle());

        TextView goalsScore = (TextView) V.findViewById(R.id.text_view_goals_score);
        goalsScore.setText(Util.scoreString(mMatch.getGoals1(), mMatch.getGoals2()));

        TextView penaltyScore = (TextView) V.findViewById(R.id.text_view_penalties_score);
        penaltyScore.setText(Util.scoreString(mMatch.getPenalty1(), mMatch.getPenalty2()));

        final CheckBox isOvertimeBox = (CheckBox) V.findViewById(R.id.checkbox_is_overtime);
        isOvertimeBox.setChecked(mMatch.isOvertime());

        final CheckBox isTechnicalWinBox = (CheckBox) V.findViewById(R.id.checkbox_is_technical_win);
        isTechnicalWinBox.setChecked(mMatch.isTechnical());

        final EditText referee = (EditText) V.findViewById(R.id.edit_text_match_judge);
        referee.setText(mMatch.getReferee());

        final EditText place = (EditText) V.findViewById(R.id.edit_text_match_location);
        place.setText(mMatch.getPlace());

        mDateButton = (Button) V.findViewById(R.id.button_match_date);
        mDateButton.setText(Util.dateString(mMatch.getStartAt()));
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickDialog();
            }
        });

        mTimeButton = (Button) V.findViewById(R.id.button_match_time);
        mTimeButton.setText(Util.timeString(mMatch.getStartAt()));
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickDialog();
            }
        });

        mSendButton = (Button) V.findViewById(R.id.button_send);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMatch.setReferee(referee.getText().toString());
                mMatch.setPlace(place.getText().toString());
                String date = mDateButton.getText().toString();
                String time = mTimeButton.getText().toString();
                mMatch.setStartAt(Util.formatUiDateAndTimeForApi(date, time));
                mMatch.setIsTechnical(isTechnicalWinBox.isChecked());
                mMatch.setIsOvertime(isOvertimeBox.isChecked());
                mProgressDialog = ProgressDialog.show(getActivity(), "", "", false);
                new HttpPatchMatchTask().execute(mMatch);
            }
        });
    }

    private class HttpPatchMatchTask extends AsyncTask<Match, Void, Void> {
        @Override
        protected Void doInBackground(Match... params) {
            try {
                Match match = params[0];
                Network.patchMatch(match);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            finally {
                mProgressDialog.dismiss();
            }
            return null;
        }
    }

    public void showDatePickDialog(){
        Calendar calendar = Util.calendarFromString(mMatch.getStartAt());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(getActivity(), new MyOnDateSetListener(), year, month, day).show();
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
        new TimePickerDialog(getActivity(), new MyOnTimeSetListener(), hour, minute, true).show();
    }

    private class MyOnTimeSetListener implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String timeString = Util.timeString(hourOfDay, minute);
            mTimeButton.setText(timeString);
        }
    }
}
