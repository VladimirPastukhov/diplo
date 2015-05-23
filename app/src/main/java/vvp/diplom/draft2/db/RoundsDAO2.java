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
import vvp.diplom.draft2.model.Tournament;

/**
 * Created by VoVqa on 23.05.2015.
 */
public class RoundsDAO2 extends BaseDaoImpl<Round, String> {

    private static final String TAG = Util.BASE_TAG + "RoundsDao";

    protected RoundsDAO2(ConnectionSource connectionSource, Class<Round> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Round> getByTournamentId(String tournamentId){
        List<Round> list = getRounds(RoundsSQL.TOURNAMENT_ID, tournamentId);
        Tournament tournament = DB.tournaments.getById(tournamentId);
        for(Round round : list){
            round.setTournament(tournament);
        }
        return list;
    }

    public Round getById(String id){
        List<Round> list = getRounds(RoundsSQL.ID, id);
        Round round = list.get(0);
        round.setTournament(DB.tournaments.getById(round.getTournamentId()));
        return round;
    }

    private List<Round> getRounds(String column, String value){
        try {
            PreparedQuery pq = queryBuilder().where().eq(column, value).prepare();
            return query(pq);
        } catch (SQLException e) {
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
