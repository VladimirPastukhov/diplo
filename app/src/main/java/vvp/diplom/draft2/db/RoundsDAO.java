package vvp.diplom.draft2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vvp.diplom.draft2.model.Round;

import static vvp.diplom.draft2.db.RoundsSQL.COLUMN_ID;
import static vvp.diplom.draft2.db.RoundsSQL.COLUMN_NAME;
import static vvp.diplom.draft2.db.RoundsSQL.COLUMN_TOURNAMENT_ID;
import static vvp.diplom.draft2.db.RoundsSQL.TABLE_NAME;
import static vvp.diplom.draft2.db.RoundsSQL.allColumns;
import static vvp.diplom.draft2.db.SQLUtil.getString;

/**
 * Created by VoVqa on 23.05.2015.
 */
public class RoundsDAO {

    private final RoundsSQL sqlHelper;

    public RoundsDAO(Context context){
        sqlHelper = new RoundsSQL(context);
    }

    protected void clean(){
        sqlHelper.clean();
    }

    public void insert(List<Round> rounds){
        for(Round round : rounds){
            insert(round);
        }
    }

    public void insert(Round round){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, round.getId());
        values.put(COLUMN_TOURNAMENT_ID, round.getTournamentId());
        values.put(COLUMN_NAME, round.getName());

        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
    }

    public List<Round> getAll(){
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, allColumns, null, null, null, null, null);
        return readRounds(cursor);
    }

    public List<Round> getByTournamentId(String tournamentId){
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        String selection = COLUMN_TOURNAMENT_ID+"=?";
        String[] selectionArgs = new String[]{tournamentId};
        Cursor cursor = db.query(TABLE_NAME, allColumns, selection, selectionArgs, null, null, null);
        return readRounds(cursor);
    }

    private static List<Round> readRounds(Cursor cursor){
        List<Round> rounds = new ArrayList<Round>();
        try {
            while(cursor.moveToNext()) {
                rounds.add(readRound(cursor));
            }
        } finally {
            cursor.close();
        }
        return rounds;
    }

    private static Round readRound(Cursor c){
        Round r = new Round();
        r.setId(getString(c, COLUMN_ID));
        r.setTournamentId(getString(c, COLUMN_TOURNAMENT_ID));
        r.setName(getString(c, COLUMN_NAME));
        return r;
    }
}
