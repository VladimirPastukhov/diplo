package vvp.diplom.draft2.db;

import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import vvp.diplom.draft2.activities.Util;
import vvp.diplom.draft2.model.Round;

/**
 * Created by VoVqa on 23.05.2015.
 */
public class RoundsDAO2 extends BaseDaoImpl<Round, String> {

    private static final String TAG = Util.BASE_TAG + "RoundsDao";

    protected RoundsDAO2(ConnectionSource connectionSource, Class<Round> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Round> getByTournamentId(String tournamentId){
        try {
            PreparedQuery pq =queryBuilder().where().eq(RoundsSQL.TOURNAMENT_ID, tournamentId).prepare();
            return query(pq);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    public Round getById(String id){
        try {
            PreparedQuery pq = queryBuilder().where().eq(RoundsSQL.ID, id).prepare();
            List<Round> list = query(pq);
            return list.get(0);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    public void insert(Round round){
        try {
            create(round);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }

    }

    public void insert(List<Round> rounds){
        for(Round round : rounds){
            insert(round);
        }
    }
}
