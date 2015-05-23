package vvp.diplom.draft2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vvp.diplom.draft2.model.Match;
import vvp.diplom.draft2.model.Team;

import static vvp.diplom.draft2.db.SQLUtil.getString;
import static vvp.diplom.draft2.db.SQLUtil.getInt;
import static vvp.diplom.draft2.db.MatchSQL.*;

/**
 * Created by VoVqa on 23.05.2015.
 */
public class MatchesDAO {

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
        values.put(ROUND_ID, match.getTeam1().getId());
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
        db.insert(TABLE_NAME, null, values);

        DB.teams.insert(match.getTeam1());
        DB.teams.insert(match.getTeam2());
    }

    public Match getById(String id){
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        String selection = ID+"=?";
        String[] selectionArgs = new String[]{id};
        Cursor cursor = db.query(TABLE_NAME, allColumns, selection, selectionArgs, null, null, null);
        Match match;
        try {
            cursor.moveToFirst();
            match = readMatch(cursor);
        } finally {
            cursor.close();
        }
        return match;
    }

    public List<Match> getAll(){
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, allColumns, null, null, null, null, null);
        return readMatchs(cursor);
    }

    private static List<Match> readMatchs(Cursor cursor){
        List<Match> matches = new ArrayList<Match>();
        try {
            while(cursor.moveToNext()) {
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
        m.setIsOvertime(getInt(c, IS_TECHNICAL) != 0);
        m.setIsOvertime(getInt(c, IS_OVERTIME) != 0);
        return m;
    }
}
