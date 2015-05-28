package vvp.diplom.draft2.db;

import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import vvp.diplom.draft2.activities.Util;
import vvp.diplom.draft2.model.Goal;

/**
 * Created by VoVqa on 23.05.2015.
 */
public class GoalsDAO extends BaseDaoImpl<Goal, String> {

    private static final String TAG = Util.BASE_TAG + "GoalsDao";

    public static final String ID = "id";
    public static final String MATCH_ID = "match_id";
    public static final String TEAM_ID = "team_id";
    public static final String PLAYER_ID = "player_id";
    public static final String ASSISTANT_ID = "assistant_id";
    public static final String MINUTE = "minute";
    public static final String ADDITIONAL_MINUTE = "additional_minute";
    public static final String IS_PENALTY = "is_penalty";
    public static final String IS_AUTOGOAL = "is_autogoal";

    protected GoalsDAO(ConnectionSource connectionSource, Class<Goal> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Goal> getByMatchId(String matchId){
        try {
            PreparedQuery pq = queryBuilder().where().eq(MATCH_ID, matchId).prepare();
            return query(pq);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    public void insert(List<Goal> goals){
        try {
            for(Goal goal : goals){
                create(goal);
            }
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }
}
