package vvp.diplom.draft2.db;

import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import vvp.diplom.draft2.activities.Util;
import vvp.diplom.draft2.model.TourPlayer;

/**
 * Created by VoVqa on 23.05.2015.
 */
public class TourPlayersDAO extends BaseDaoImpl<TourPlayer, String> {

    private static final String TAG = Util.BASE_TAG + "TourPlayerDao";

    public static final String ID = "id";
    public static final String TOURNAMENT_ID = "tournament_id";
    public static final String TEAM_ID = "team_id";
    public static final String PLAYER_ID = "player_id";

    protected TourPlayersDAO(ConnectionSource connectionSource, Class<TourPlayer> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public void insert(List<TourPlayer> tourPlayers){
        for(TourPlayer tourPlayer : tourPlayers){
            try {
                create(tourPlayer);
            } catch (SQLException e) {
                Log.d(TAG, e.getMessage(), e);
            }
            DB.players.insert(tourPlayer.getPlayer());
        }
    }

    public List<TourPlayer> getByTournamentId(String tourId){
        try {
            PreparedQuery pq = queryBuilder().where().eq(TOURNAMENT_ID, tourId).prepare();
            return query(pq);
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage(), e);
            return null;
        }
    }

    public List<TourPlayer> getByTeamIdAndTourId(String teamId, String tourId){
        try {
            PreparedQuery pq = queryBuilder().where().eq(TEAM_ID, teamId).and().eq(TOURNAMENT_ID, tourId).prepare();
            return query(pq);
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage(), e);
            return null;
        }
    }
}
