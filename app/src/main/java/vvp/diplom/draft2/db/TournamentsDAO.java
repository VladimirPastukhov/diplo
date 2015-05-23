package vvp.diplom.draft2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vvp.diplom.draft2.model.Tournament;
import static vvp.diplom.draft2.db.TournamentsSQL.*;


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
        Cursor c = db.query(TABLE_NAME, allColumns, selection, selectionArgs, null, null, null);
        c.moveToFirst();

        int idIndex = c.getColumnIndex(COLUMN_ID);
        int titleIndex = c.getColumnIndex(COLUMN_TITLE);
        int startDateIndex = c.getColumnIndex(COLUMN_START_DATE);
        int endDateIndex = c.getColumnIndex(COLUMN_END_DATE);

        Tournament t = new Tournament();
        t.setId(c.getString(idIndex));
        t.setTitle(c.getString(titleIndex));
        t.setStartDate(c.getString(startDateIndex));
        t.setEndDate(c.getString(endDateIndex));

        c.close();
        return t;
    }

    public List<Tournament> getAll(){
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, allColumns, null, null, null, null, null);
        return readTournaments(cursor);
    }

    private static List<Tournament> readTournaments(Cursor c){
        int idIndex = c.getColumnIndex(COLUMN_ID);
        int titleIndex = c.getColumnIndex(COLUMN_TITLE);
        int startDateIndex = c.getColumnIndex(COLUMN_START_DATE);
        int endDateIndex = c.getColumnIndex(COLUMN_END_DATE);

        List<Tournament> tournaments = new ArrayList<Tournament>();
        try {
            while(c.moveToNext()) {
                Tournament t = new Tournament();
                t.setId(c.getString(idIndex));
                t.setTitle(c.getString(titleIndex));
                t.setStartDate(c.getString(startDateIndex));
                t.setEndDate(c.getString(endDateIndex));
                tournaments.add(t);
            }
        } finally {
            c.close();
        }
        return tournaments;
    }
}
