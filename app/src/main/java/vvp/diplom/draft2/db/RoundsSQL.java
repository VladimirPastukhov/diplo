package vvp.diplom.draft2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static vvp.diplom.draft2.db.SQLUtil.*;

/**
 * Created by VoVqa on 23.05.2015.
 */
public class RoundsSQL extends SQLiteOpenHelper {

    protected static final String TABLE_NAME = "rounds";
    protected static final String ID = "id";
    protected static final String TOURNAMENT_ID = "tournament_id";
    protected static final String NAME = "name";
    protected static final String[] allColumns
            = {ID, TOURNAMENT_ID, NAME};

    protected static final String CREATE =
            CREATE_TABLE_ + TABLE_NAME + " (" +
                    ID + _INTEGER_PRIMARY_KEY_COMMA +
                    TOURNAMENT_ID + _INTEGER_COMMA +
                    NAME + _TEXT_COMMA +
                    formatForeignKey(TOURNAMENT_ID, TournamentsSQL.TABLE_NAME, TournamentsSQL.ID)+
                    ")";

    protected static final String DELETE = DROP_TABLE_IF_EXISTS_ + TABLE_NAME;

    protected RoundsSQL(Context context) {
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
