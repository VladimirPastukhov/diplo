package vvp.diplom.draft2.db;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import vvp.diplom.draft2.db.dao.GoalsDao;
import vvp.diplom.draft2.db.dao.MatchPlayersDao;
import vvp.diplom.draft2.db.dao.MatchesDao;
import vvp.diplom.draft2.db.dao.ORMLite;
import vvp.diplom.draft2.db.dao.PlayersDao;
import vvp.diplom.draft2.db.dao.RoundsDao;
import vvp.diplom.draft2.db.dao.TeamsDao;
import vvp.diplom.draft2.db.dao.TourPlayersDao;
import vvp.diplom.draft2.db.dao.TournamentsDao;

/**
 * Created by VoVqa on 23.05.2015.
 */
public class DB {
    public static final String NAME = "Sportspring.dp";
    public static final int VERSION = 1;

    public static PlayersDao players;

    public static GoalsDao goals;
    public static RoundsDao rounds;
    public static TourPlayersDao tourPlayers;
    public static MatchPlayersDao matchPlayers;
    public static TournamentsDao tournaments;
    public static MatchesDao matches;
    public static TeamsDao teams;

    private static vvp.diplom.draft2.db.dao.ORMLite ORMLite;

    private DB(){}

    public static void init(Context context){

        ORMLite = OpenHelperManager.getHelper(context, ORMLite.class);
        ORMLite.clear();
        ORMLite.init();
        players = ORMLite.players;
        goals = ORMLite.goals;
        rounds = ORMLite.rounds;
        tourPlayers = ORMLite.tourPlayers;
        matchPlayers = ORMLite.matchPlayers;
        tournaments = ORMLite.tournaments;
        matches = ORMLite.matches;
        teams = ORMLite.teams;
    }

    public static void shutdown(){OpenHelperManager.releaseHelper();}
}
