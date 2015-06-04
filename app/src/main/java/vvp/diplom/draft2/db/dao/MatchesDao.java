package vvp.diplom.draft2.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import vvp.diplom.draft2.activities.Util;
import vvp.diplom.draft2.db.DB;
import vvp.diplom.draft2.model.Match;

/**
 * Created by VoVqa on 04.06.2015.
 */
public class MatchesDao extends BaseDaoImpl<Match, String>{
    private static final String TAG = Util.BASE_TAG + "MatchesDao";

    public static final String TABLE_NAME = "matches";
    public static final String ID = "id";
    public static final String ROUND_ID = "round_id";
    public static final String TEAM1_ID = "team1_id";
    public static final String TEAM2_ID = "team2_id";
    public static final String START_AT = "start_at";
    public static final String GOALS1 = "goals1";
    public static final String GOALS2 = "goals2";
    public static final String PENALTY1 = "penalty1";
    public static final String PENALTY2 = "penalty2";
    public static final String REFEREE = "referee";
    public static final String PLACE = "place";
    public static final String IS_TECHNICAL = "is_technical";
    public static final String IS_OVERTIME = "is_overtime";

    protected MatchesDao(ConnectionSource connectionSource, Class<Match> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public void insert(List<Match> matches){
        for(Match match : matches){
            insert(match);
        }
    }

    public void insert(Match match){
        try {
            createOrUpdate(match);
            DB.teams.insert(match.getTeam1());
            DB.teams.insert(match.getTeam2());
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage(), e);
        }
    }

    public Match getById(String id){
        try {
            Match match = queryForId(id);
            loadSubfields(match);
            return match;
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage(), e);
            return null;
        }
    }

    public List<Match> getByRoundId(String roundId){

        try {
            PreparedQuery pq = queryBuilder().where().eq(ROUND_ID, roundId).prepare();
            List<Match> matches = query(pq);
            for(Match match : matches){
                loadSubfields(match);
            }
            return matches;
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage(), e);
            return null;
        }
    }

    private static void loadSubfields(Match match){
        match.setRound(DB.rounds.getById(match.getRoundId()));
        match.setTeam1(DB.teams.getById(match.getTeam1Id()));
        match.setTeam2(DB.teams.getById(match.getTeam2Id()));
    }
}
