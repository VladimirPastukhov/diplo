package vvp.diplom.draft2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static vvp.diplom.draft2.db.SQLUtil.CREATE_TABLE_;
import static vvp.diplom.draft2.db.SQLUtil.DROP_TABLE_IF_EXISTS_;
import static vvp.diplom.draft2.db.SQLUtil._INTEGER_PRIMARY_KEY_COMMA;
import static vvp.diplom.draft2.db.SQLUtil._TEXT;
import static vvp.diplom.draft2.db.SQLUtil._TEXT_COMMA;

/**
 * Created by VoVqa on 23.05.2015.
 */
public class TournamentsSQL extends SQLiteOpenHelper{

    protected static final String TABLE_NAME = "tournaments";
    protected static final String COLUMN_ID = "id";
    protected static final String COLUMN_TITLE = "title";
    protected static final String COLUMN_START_DATE = "start_date";
    protected static final String COLUMN_END_DATE = "end_date";
    protected static final String[] allColumns
            = {COLUMN_ID, COLUMN_TITLE, COLUMN_START_DATE, COLUMN_END_DATE};

    protected static final String CREATE =
            CREATE_TABLE_ + TABLE_NAME + " (" +
            COLUMN_ID + _INTEGER_PRIMARY_KEY_COMMA +
            COLUMN_TITLE + _TEXT_COMMA +
            COLUMN_START_DATE + _TEXT_COMMA +
            COLUMN_END_DATE + _TEXT +
            ")";

    protected static final String DELETE = DROP_TABLE_IF_EXISTS_ + TABLE_NAME;

    protected TournamentsSQL(Context context) {
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

    public void clean(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(DELETE);
        db.execSQL(CREATE);
    }
}
