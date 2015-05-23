package vvp.diplom.draft2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static vvp.diplom.draft2.db.SQLUtil.CREATE_TABLE_;
import static vvp.diplom.draft2.db.SQLUtil.DROP_TABLE_IF_EXISTS_;
import static vvp.diplom.draft2.db.SQLUtil._INTEGER_PRIMARY_KEY_COMMA;
import static vvp.diplom.draft2.db.SQLUtil._INTEGER_COMMA;
import static vvp.diplom.draft2.db.SQLUtil._TEXT_COMMA;
import static vvp.diplom.draft2.db.SQLUtil.formatForeignKey;

/**
 * Created by VoVqa on 23.05.2015.
 */
public class MatchSQL extends SQLiteOpenHelper {

    protected static final String TABLE_NAME = "matches";
    protected static final String ID = "id";
    protected static final String ROUND_ID = "round_id";
    protected static final String TEAM1_ID = "team1_id";
    protected static final String TEAM2_ID = "team2_id";
    protected static final String START_AT = "start_at";
    protected static final String GOALS1 = "goals1";
    protected static final String GOALS2 = "goals2";
    protected static final String PENALTY1 = "penalty1";
    protected static final String PENALTY2 = "penalty2";
    protected static final String REFEREE = "referee";
    protected static final String PLACE = "place";
    protected static final String IS_TECHNICAL = "is_technical";
    protected static final String IS_OVERTIME = "is_overtime";

    protected static final String[] allColumns
            = {ID, ROUND_ID, TEAM1_ID, TEAM2_ID, START_AT, GOALS1, GOALS2,
            PENALTY1, PENALTY2, REFEREE, PLACE, IS_TECHNICAL, IS_OVERTIME};

    protected static final String CREATE =
            CREATE_TABLE_ + TABLE_NAME + " (" +
                    ID + _INTEGER_PRIMARY_KEY_COMMA +
                    ROUND_ID + _INTEGER_COMMA +
                    TEAM1_ID + _INTEGER_COMMA +
                    TEAM2_ID + _INTEGER_COMMA +
                    START_AT + _TEXT_COMMA +
                    GOALS1 + _INTEGER_COMMA +
                    GOALS2 + _INTEGER_COMMA +
                    PENALTY1 + _INTEGER_COMMA +
                    PENALTY2 + _INTEGER_COMMA +
                    REFEREE + _TEXT_COMMA +
                    PLACE + _TEXT_COMMA +
                    IS_TECHNICAL + _INTEGER_COMMA +
                    IS_OVERTIME + _INTEGER_COMMA +
                    formatForeignKey(ROUND_ID, RoundsSQL.TABLE_NAME, RoundsSQL.ID)+
                    ")";

    protected static final String DELETE = DROP_TABLE_IF_EXISTS_ + TABLE_NAME;

    protected MatchSQL(Context context) {
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
