package vvp.diplom.draft2.activities;

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

    private Match mMatch;

    private View V;

    private TextView mTeam1TitleTextView;
    private TextView mTeam2TitleTextView;
    private TextView mGoalsScoreTextView;
    private TextView mPenaltyScoreTextView;
    private EditText mLocationEditText;
    private EditText mRefereeEditText;
    private CheckBox mIsOvertimeCheckBox;
    private CheckBox mIsTechnicalWinCheckBox;
    private Button mDateButton;
    private Button mTimeButton;

    public MatchSummaryFragment create(Match match){
        MatchSummaryFragment matchSummaryFragment = new MatchSummaryFragment();
        matchSummaryFragment.mMatch = match;
        return matchSummaryFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        V = inflater.inflate(R.layout.fragment_match_summary, container, false);
        return V;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        updateViews();
    }

    private void initViews(){
        mTeam1TitleTextView = (TextView) V.findViewById(R.id.text_view_title_team_1);
        mTeam2TitleTextView = (TextView) V.findViewById(R.id.text_view_title_team_2);
        mGoalsScoreTextView = (TextView) V.findViewById(R.id.text_view_goals_score);
        mPenaltyScoreTextView = (TextView) V.findViewById(R.id.text_view_penalties_score);
        mIsOvertimeCheckBox= (CheckBox) V.findViewById(R.id.checkbox_is_overtime);
        mIsTechnicalWinCheckBox = (CheckBox) V.findViewById(R.id.checkbox_is_technical_win);
        mRefereeEditText = (EditText) V.findViewById(R.id.edit_text_match_referee);
        mLocationEditText = (EditText) V.findViewById(R.id.edit_text_match_location);
        mDateButton = (Button) V.findViewById(R.id.button_match_date);
        mTimeButton = (Button) V.findViewById(R.id.button_match_time);
    }

    private void updateViews(){
        mTeam1TitleTextView.setText(mMatch.getTeam1().getTitle());
        mTeam2TitleTextView.setText(mMatch.getTeam2().getTitle());
        mGoalsScoreTextView.setText(Util.scoreString(mMatch.getGoals1(), mMatch.getGoals2()));
        mPenaltyScoreTextView.setText(Util.scoreString(mMatch.getPenalty1(), mMatch.getPenalty2()));
        mIsOvertimeCheckBox.setChecked(mMatch.isOvertime());
        mIsTechnicalWinCheckBox.setChecked(mMatch.isTechnical());
        mRefereeEditText.setText(mMatch.getReferee());
        mLocationEditText.setText(mMatch.getPlace());
        mDateButton.setText(Util.dateString(mMatch.getStartAt()));
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickDialog();
            }
        });
        mTimeButton.setText(Util.timeString(mMatch.getStartAt()));
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickDialog();
            }
        });
    }

    protected void updateModel(){
        mMatch.setReferee(mRefereeEditText.getText().toString());
        mMatch.setPlace(mLocationEditText.getText().toString());
        String date = mDateButton.getText().toString();
        String time = mTimeButton.getText().toString();
        mMatch.setStartAt(Util.formatUiDateAndTimeForApi(date, time));
        mMatch.setIsOvertime(mIsOvertimeCheckBox.isChecked());
        mMatch.setIsTechnical(mIsTechnicalWinCheckBox.isChecked());
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

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause(){
        super.onPause();
        updateModel();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
