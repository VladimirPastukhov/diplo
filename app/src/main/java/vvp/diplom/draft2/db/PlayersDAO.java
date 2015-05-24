package vvp.diplom.draft2.db;

import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vvp.diplom.draft2.activities.Util;
import vvp.diplom.draft2.model.Player;
import vvp.diplom.draft2.model.TourPlayer;

/**
 * Created by VoVqa on 23.05.2015.
 */
public class PlayersDAO extends BaseDaoImpl<Player, String>{

    private static final String TAG = Util.BASE_TAG + "PlayersDao";

    public static final String ID = "id";
    public static final String NAME = "name";

    protected PlayersDAO(ConnectionSource connectionSource, Class<Player> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public void insert(Player player){
        try {
            create(player);
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage(), e);
        }
    }

    public Player getById(String id){
        List<Player> list = null;
        try {
            list = query(queryBuilder().where().eq(ID, id).prepare());
            return list.get(0);
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage(), e);
            return null;
        }
    }

    public List<Player> getByIdList(List<String> ids){
        Log.d(TAG, "Diaposon ids "+ids);
        try {
            return query(queryBuilder().where().in(ID, ids).prepare());
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage(), e);
            return null;
        }
    }

    public List<Player> getByTeamIdAndTournamentId(String teamId, String tournamentId){
        List<String> ids = new ArrayList<>();
        List<TourPlayer> tourPlayers = DB.tourPlayers.getByTeamIdAndTourId(teamId, tournamentId);
        Log.d(TAG, "tourPlayers "+tourPlayers);
        for(TourPlayer tourPlayer : tourPlayers){
            ids.add(tourPlayer.getPlayerId());
        }
        return getByIdList(ids);
    }
}
