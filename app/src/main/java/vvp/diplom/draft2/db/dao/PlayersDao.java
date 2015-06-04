package vvp.diplom.draft2.db.dao;

import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vvp.diplom.draft2.activities.Util;
import vvp.diplom.draft2.db.DB;
import vvp.diplom.draft2.model.Player;
import vvp.diplom.draft2.model.TourPlayer;

/**
 * Created by VoVqa on 23.05.2015.
 */
public class PlayersDao extends BaseDaoImpl<Player, String>{

    private static final String TAG = Util.BASE_TAG + "PlayersDao";

    public static final String TABLE_NAME = "players";
    public static final String ID = "id";
    public static final String NAME = "name";

    protected PlayersDao(ConnectionSource connectionSource, Class<Player> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public void insert(Player player){
        try {
            createOrUpdate(player);
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage(), e);
        }
    }

    public Player getById(String id){
        try {
            return queryForId(id);
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage(), e);
            return null;
        }
    }

    public List<Player> getByTournamentId(String tournamentId){
        List<TourPlayer> tourPlayers = DB.tourPlayers.getByTournamentId(tournamentId);
        Log.d(TAG, "tourPlayers1 "+tourPlayers);
        return getByIds(extractPlayerIds(tourPlayers));
    }

    public List<Player> getByTeamIdAndTournamentId(String teamId, String tournamentId){
        List<TourPlayer> tourPlayers = DB.tourPlayers.getByTeamIdAndTourId(teamId, tournamentId);
        Log.d(TAG, "tourPlayers2 "+tourPlayers);
        return getByIds(extractPlayerIds(tourPlayers));
    }

    public List<Player> getByIds(List<String> ids){
        Log.d(TAG, "Range ids "+ids);
        try {
            return query(queryBuilder().where().in(ID, ids).prepare());
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage(), e);
            return null;
        }
    }

    private static List<String> extractPlayerIds(List<TourPlayer> tourPlayers){
        List<String> ids = new ArrayList<>();
        for(TourPlayer tourPlayer : tourPlayers){
            ids.add(tourPlayer.getPlayerId());
        }
        return ids;
    }
}
