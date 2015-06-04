package vvp.diplom.draft2.db.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import vvp.diplom.draft2.activities.Util;
import vvp.diplom.draft2.db.DB;
import vvp.diplom.draft2.model.Goal;
import vvp.diplom.draft2.model.Match;
import vvp.diplom.draft2.model.MatchPlayer;
import vvp.diplom.draft2.model.Player;
import vvp.diplom.draft2.model.Round;
import vvp.diplom.draft2.model.Team;
import vvp.diplom.draft2.model.TourPlayer;
import vvp.diplom.draft2.model.Tournament;

/**
 * Created by VoVqa on 23.05.2015.
 */
public class ORMLite extends OrmLiteSqliteOpenHelper {

    private static final String TAG = Util.BASE_TAG + "ORMLite";

    public static PlayersDao players;
    public static GoalsDao goals;
    public static RoundsDao rounds;
    public static TourPlayersDao tourPlayers;
    public static MatchPlayersDao matchPlayers;
    public static TournamentsDao tournaments;
    public static MatchesDao matches;
    public static TeamsDao teams;

    public ORMLite(Context context){
        super(context, DB.NAME, null, DB.VERSION);
    }

    public void init(){
        try {
            ConnectionSource cs = getConnectionSource();
            players = new PlayersDao(cs, Player.class);
            goals = new GoalsDao(cs, Goal.class);
            rounds = new RoundsDao(cs, Round.class);
            tourPlayers = new TourPlayersDao(cs, TourPlayer.class);
            matchPlayers = new MatchPlayersDao(cs, MatchPlayer.class);
            tournaments = new TournamentsDao(cs, Tournament.class);
            matches = new MatchesDao(cs, Match.class);
            teams = new TeamsDao(cs, Team.class);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    public void clear(){
        try {
            ConnectionSource cs = getConnectionSource();
            TableUtils.dropTable(cs, Player.class, true);
            TableUtils.dropTable(cs, Goal.class, true);
            TableUtils.dropTable(cs, Round.class, true);
            TableUtils.dropTable(cs, TourPlayer.class, true);
            TableUtils.dropTable(cs, MatchPlayer.class, true);
            TableUtils.dropTable(cs, Tournament.class, true);
            TableUtils.dropTable(cs, Match.class, true);
            TableUtils.dropTable(cs, Team.class, true);
            createTables(cs);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    void createTables(ConnectionSource cs) throws SQLException {
        TableUtils.createTable(cs, Player.class);
        TableUtils.createTable(cs, Goal.class);
        TableUtils.createTable(cs, Round.class);
        TableUtils.createTable(cs, TourPlayer.class);
        TableUtils.createTable(cs, MatchPlayer.class);
        TableUtils.createTable(cs, Tournament.class);
        TableUtils.createTable(cs, Match.class);
        TableUtils.createTable(cs, Team.class);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            createTables(connectionSource);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        clear();
    }
}
