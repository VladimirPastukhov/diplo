package vvp.diplom.draft2.db.dao;

import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import vvp.diplom.draft2.activities.Util;
import vvp.diplom.draft2.model.Tournament;

/**
 * Created by VoVqa on 04.06.2015.
 */
public class TournamentsDao extends BaseDaoImpl<Tournament, String> {

    private static final String TAG = Util.BASE_TAG + "ToursDao";

    public static final String TABLE_NAME = "tournaments";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String START_DATE = "start_date";
    public static final String END_DATE = "end_date";

    protected TournamentsDao(ConnectionSource connectionSource, Class<Tournament> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public void insert(List<Tournament> tournaments){
        for(Tournament tournament : tournaments){
            insert(tournament);
        }
    }

    public void insert(Tournament tournament){
        try {
            createOrUpdate(tournament);
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage(), e);
        }
    }

    public Tournament getById(String id){
        try {
            return queryForId(id);
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage(), e);
            return null;
        }
    }

    public List<Tournament> getAll(){
        try {
            return queryForAll();
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage(), e);
            return null;
        }
    }
}
