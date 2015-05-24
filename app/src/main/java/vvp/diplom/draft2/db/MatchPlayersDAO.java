package vvp.diplom.draft2.db;

import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vvp.diplom.draft2.activities.Util;
import vvp.diplom.draft2.model.MatchPlayer;

/**
 * Created by VoVqa on 24.05.2015.
 */
public class MatchPlayersDAO extends BaseDaoImpl<MatchPlayer, String>{

    public static final String TAG = Util.BASE_TAG + "MatchPlayersDao";

    public static final String ID = "id";
    public static final String MATCH_ID = "match_id";
    public static final String TEAM_ID = "team_id";
    public static final String PLAYER_ID = "player_id";
    public static final String STATUS = "status";
    public static final String IS_CAPTAIN = "is_captain";
    public static final String IS_GOALKEEPER = "is_goalkeeper";

    protected MatchPlayersDAO(ConnectionSource connectionSource, Class<MatchPlayer> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public void insert(List<MatchPlayer> matchPlayers){
        for(MatchPlayer matchPlayer : matchPlayers){
            insert(matchPlayer);
        }
    }

    public void insert(MatchPlayer matchPlayer){
        try {
            createOrUpdate(matchPlayer);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    public Set<String> getPlayerIdsByMatchIdAndTeamId(String matchId, String teamId){
        return extractPlayerIds(getByMatchIdAndTeamId(matchId, teamId));
    }

    public List<MatchPlayer> getByMatchIdAndTeamId(String matchId, String teamId){
        PreparedQuery pq  = null;
        try {
            pq = queryBuilder().where().eq(MATCH_ID, matchId).and().eq(TEAM_ID, teamId).prepare();
            return query(pq);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    private static Set<String> extractPlayerIds(List<MatchPlayer> matchPlayers){
        Set<String> ids = new HashSet<>();
        for(MatchPlayer matchPlayer : matchPlayers){
            ids.add(matchPlayer.getPlayerId());
        }
        return ids;
    }
}
