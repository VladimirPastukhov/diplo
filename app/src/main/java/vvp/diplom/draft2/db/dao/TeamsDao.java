package vvp.diplom.draft2.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import vvp.diplom.draft2.activities.Util;
import vvp.diplom.draft2.model.Team;

/**
 * Created by VoVqa on 04.06.2015.
 */
public class TeamsDao extends BaseDaoImpl<Team, String>{

    private static final String TAG = Util.BASE_TAG + "TeamsDao";
    public static final String TABLE_NAME = "teams";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String IMAGE_PATH = "name";

    protected TeamsDao(ConnectionSource connectionSource, Class<Team> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public void insert(Team team){
        try {
            createOrUpdate(team);
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage(), e);
        }
    }

    public Team getById(String id){
        try {
            return queryForId(id);
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage(), e);
            return null;
        }
    }
}
