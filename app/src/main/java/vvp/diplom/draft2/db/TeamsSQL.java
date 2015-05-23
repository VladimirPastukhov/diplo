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
public class TeamsSQL extends SQLiteOpenHelper{

    protected static final String TABLE_NAME = "teams";
    protected static final String ID = "id";
    protected static final String TITLE = "title";
    protected static final String IMAGE_PATH = "name";
    protected static final String[] allColumns
            = {ID, TITLE, IMAGE_PATH};

    protected static final String CREATE =
            CREATE_TABLE_ + TABLE_NAME + " (" +
                    ID + _INTEGER_PRIMARY_KEY_COMMA +
                    TITLE + _TEXT_COMMA +
                    IMAGE_PATH + _TEXT +
                    ")";

    protected static final String DELETE = DROP_TABLE_IF_EXISTS_ + TABLE_NAME;

    protected TeamsSQL(Context context) {
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
