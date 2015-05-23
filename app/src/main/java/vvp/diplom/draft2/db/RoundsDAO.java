package vvp.diplom.draft2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vvp.diplom.draft2.model.Round;

import static vvp.diplom.draft2.db.RoundsSQL.ID;
import static vvp.diplom.draft2.db.RoundsSQL.NAME;
import static vvp.diplom.draft2.db.RoundsSQL.TOURNAMENT_ID;
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
        values.put(ID, round.getId());
        values.put(TOURNAMENT_ID, round.getTournamentId());
        values.put(NAME, round.getName());

        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
    }

    public Round getById(String id){
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        String selection = ID +"=?";
        String[] selectionArgs = new String[]{id};
        Cursor cursor = db.query(TABLE_NAME, allColumns, selection, selectionArgs, null, null, null);
        Round round;
        try {
            cursor.moveToFirst();
            round = readRound(cursor);
        } finally {
            cursor.close();
        }
        return round;
    }

    public List<Round> getAll(){
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, allColumns, null, null, null, null, null);
        return readRounds(cursor);
    }

    public List<Round> getByTournamentId(String tournamentId){
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        String selection = TOURNAMENT_ID +"=?";
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
        r.setId(getString(c, ID));
        r.setTournamentId(getString(c, TOURNAMENT_ID));
        r.setName(getString(c, NAME));
        return r;
    }
}
