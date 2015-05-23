package vvp.diplom.draft2.db;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import vvp.diplom.draft2.model.Goal;

/**
 * Created by VoVqa on 23.05.2015.
 */
public class GoalsDAO extends BaseDaoImpl<Goal, String> {
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

    public List<Goal> getByMatchId(String matchId) throws SQLException {
        QueryBuilder<Goal, String> queryBuilder = queryBuilder();
        queryBuilder.where().eq(MATCH_ID, matchId);
        PreparedQuery preparedQuery = queryBuilder.prepare();
        return query(preparedQuery);
    }
}
