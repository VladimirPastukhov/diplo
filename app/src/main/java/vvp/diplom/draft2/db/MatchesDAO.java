package vvp.diplom.draft2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vvp.diplom.draft2.activities.Util;
import vvp.diplom.draft2.model.Match;
import vvp.diplom.draft2.model.Round;
import vvp.diplom.draft2.model.Team;
import vvp.diplom.draft2.network.apiLists.Matches;

import static vvp.diplom.draft2.db.SQLUtil.getString;
import static vvp.diplom.draft2.db.SQLUtil.getInt;
import static vvp.diplom.draft2.db.MatchSQL.*;

/**
 * Created by VoVqa on 23.05.2015.
 */
public class MatchesDAO {

    private static final String TAG = Util.BASE_TAG + "MatchesDao";

    private final MatchSQL sqlHelper;

    public MatchesDAO(Context context){
        sqlHelper = new MatchSQL(context);
    }

    protected void clean(){
        sqlHelper.clean();
    }

    public void insert(List<Match> matches){
        for(Match match : matches){
            insert(match);
        }
    }

    public void insert(Match match){
        ContentValues values = new ContentValues();
        values.put(ID, match.getId());
        values.put(ROUND_ID, match.getRoundId());
        values.put(TEAM1_ID, match.getTeam1().getId());
        values.put(TEAM2_ID, match.getTeam2().getId());
        values.put(START_AT, match.getStartAt());
        values.put(GOALS1, match.getGoals1());
        values.put(GOALS2, match.getGoals2());
        values.put(PENALTY1, match.getPenalty1());
        values.put(REFEREE, match.getReferee());
        values.put(PLACE, match.getPlace());
        values.put(IS_TECHNICAL, match.isTechnical() ? 1 : 0);
        values.put(IS_OVERTIME, match.isOvertime() ? 1 : 0);

        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        DB.teams.insert(match.getTeam1());
        DB.teams.insert(match.getTeam2());
    }

    public Match getById(String id){
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        String selection = ID+"=?";
        String[] selectionArgs = new String[]{id};
        Cursor cursor = db.query(TABLE_NAME, allColumns, selection, selectionArgs, null, null, null);
        Match match;
//        List<Match> matches = readMatches(cursor);
//        Log.d(TAG, "getMatchById list "+matches);
        try {
            cursor.moveToFirst();
            match = readMatch(cursor);
            match.setRound(DB.rounds.getById(match.getRoundId()));
        } finally {
            cursor.close();
        }
        return match;
    }

    public List<Match> getByRoundId(String roundId){
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        String selection = ROUND_ID+"=?";
        String[] selectionArgs = new String[]{roundId};
        Cursor cursor = db.query(TABLE_NAME, allColumns, selection, selectionArgs, null, null, null);
        List<Match> list = readMatches(cursor);
        Round round = DB.rounds.getById(roundId);
        for(Match match : list){
            match.setRound(round);
        }
        return list;
    }

    private static List<Match> readMatches(Cursor cursor){
        ArrayList<Match> matches = new ArrayList<>();
        try {
            while(cursor.moveToNext()){
                matches.add(readMatch(cursor));
            }
        } finally {
            cursor.close();
        }
        return matches;
    }

    private static Match readMatch(Cursor c){
        Match m = new Match();
        m.setId(getString(c, ID));
        m.setRoundId(getString(c, ROUND_ID));
        m.setTeam1(DB.teams.getById(getString(c, TEAM1_ID)));
        m.setTeam2(DB.teams.getById(getString(c, TEAM2_ID)));
        m.setStartAt(getString(c, START_AT));
        m.setGoals2(getString(c, GOALS1));
        m.setGoals2(getString(c, GOALS2));
        m.setPenalty1(getString(c, PENALTY1));
        m.setPenalty2(getString(c, PENALTY2));
        m.setReferee(getString(c, REFEREE));
        m.setPlace(getString(c, PLACE));
        m.setIsTechnical(getInt(c, IS_TECHNICAL) != 0);
        m.setIsOvertime(getInt(c, IS_OVERTIME) != 0);
        return m;
    }
}
