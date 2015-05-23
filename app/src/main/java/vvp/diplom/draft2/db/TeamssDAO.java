package vvp.diplom.draft2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import vvp.diplom.draft2.model.Team;

import static vvp.diplom.draft2.db.SQLUtil.getString;
import static vvp.diplom.draft2.db.TeamsSQL.COLUMN_ID;
import static vvp.diplom.draft2.db.TeamsSQL.COLUMN_IMAGE_PATH;
import static vvp.diplom.draft2.db.TeamsSQL.COLUMN_TITLE;
import static vvp.diplom.draft2.db.TeamsSQL.TABLE_NAME;
import static vvp.diplom.draft2.db.TeamsSQL.allColumns;

/**
 * Created by VoVqa on 23.05.2015.
 */
public class TeamssDAO {

    private final TeamsSQL sqlHelper;

    public TeamssDAO(Context context){
        sqlHelper = new TeamsSQL(context);
    }

    protected void clean(){
        sqlHelper.clean();
    }

    public void insert(Team team){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, team.getId());
        values.put(COLUMN_TITLE, team.getTitle());
        values.put(COLUMN_IMAGE_PATH, team.getImagetPath());

        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
    }

    public Team getById(String id){
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        String selection = COLUMN_ID+"=?";
        String[] selectionArgs = new String[]{id};
        Cursor cursor = db.query(TABLE_NAME, allColumns, selection, selectionArgs, null, null, null);
        Team tournament;
        try {
            cursor.moveToFirst();
            tournament = readTeam(cursor);
        } finally {
            cursor.close();
        }
        return tournament;
    }

    private static Team readTeam(Cursor c){
        Team t = new Team();
        t.setId(getString(c, COLUMN_ID));
        t.setTitle(getString(c, COLUMN_TITLE));
        t.setImagetPath(getString(c, COLUMN_IMAGE_PATH));
        return t;
    }
}
