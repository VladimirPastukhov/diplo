package vvp.diplom.draft2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vvp.diplom.draft2.model.Tournament;
import static vvp.diplom.draft2.db.TournamentsSQL.*;
import static vvp.diplom.draft2.db.SQLUtil.*;


/**
 * Created by VoVqa on 23.05.2015.
 */
public class TournamentsDAO {

    private final TournamentsSQL sqlHelper;

    public TournamentsDAO(Context context){
        sqlHelper = new TournamentsSQL(context);
    }

    protected void clean(){
        sqlHelper.clean();
    }

    public void insert(List<Tournament> tournaments){
        for(Tournament tournament : tournaments){
            insert(tournament);
        }
    }

    public void insert(Tournament tournament){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, tournament.getId());
        values.put(COLUMN_TITLE, tournament.getTitle());
        values.put(COLUMN_START_DATE, tournament.getStartDate());
        values.put(COLUMN_END_DATE, tournament.getEndDate());

        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
    }

    public Tournament getById(String id){
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        String selection = COLUMN_ID+"=?";
        String[] selectionArgs = new String[]{id};
        Cursor cursor = db.query(TABLE_NAME, allColumns, selection, selectionArgs, null, null, null);
        Tournament tournament;
        try {
            cursor.moveToFirst();
            tournament = readTournament(cursor);
        } finally {
            cursor.close();
        }
        return tournament;
    }

    public List<Tournament> getAll(){
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, allColumns, null, null, null, null, null);
        return readTournaments(cursor);
    }

    private static List<Tournament> readTournaments(Cursor cursor){
        List<Tournament> tournaments = new ArrayList<Tournament>();
        try {
            while(cursor.moveToNext()) {
                tournaments.add(readTournament(cursor));
            }
        } finally {
            cursor.close();
        }
        return tournaments;
    }

    private static Tournament readTournament(Cursor c){
        Tournament t = new Tournament();
        t.setId(getString(c, COLUMN_ID));
        t.setTitle(getString(c, COLUMN_TITLE));
        t.setStartDate(getString(c, COLUMN_START_DATE));
        t.setEndDate(getString(c, COLUMN_END_DATE));
        return t;
    }
}
