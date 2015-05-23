package vvp.diplom.draft2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import vvp.diplom.draft2.model.Tournament;

import static vvp.diplom.draft2.db.SQL.*;

/**
 * Created by VoVqa on 23.05.2015.
 */
public class TournamentSQLHelper extends SQLiteOpenHelper{

    private static final String TABLE_NAME = "tournaments";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_START_DATE = "start_date";
    private static final String COLUMN_END_DATE = "end_date";
    private static final String[] allColumns
            = {COLUMN_ID, COLUMN_TITLE, COLUMN_START_DATE, COLUMN_END_DATE};

    private static final String CREATE =
            CREATE_TABLE_ + TABLE_NAME + " (" +
            COLUMN_ID + _INTEGER_PRIMARY_KEY_COMMA +
            COLUMN_TITLE + _TEXT_COMMA +
            COLUMN_START_DATE + _TEXT_COMMA +
            COLUMN_END_DATE + _TEXT +
            " )";

    private static final String DELETE = DROP_TABLE_IF_EXISTS_ + TABLE_NAME;


    public TournamentSQLHelper(Context context) {
        super(context, DB.NAME, null, DB.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE);
        onCreate(db);
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

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
    }

    public List<Tournament> getAll(){
        SQLiteDatabase db = getReadableDatabase();
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
