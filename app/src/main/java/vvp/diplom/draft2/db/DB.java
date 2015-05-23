package vvp.diplom.draft2.db;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * Created by VoVqa on 23.05.2015.
 */
public class DB {
    public static final String NAME = "Sportspring.dp";
    public static final int VERSION = 1;

    public static TournamentsDAO tournaments;
    public static TeamssDAO teams;
    public static MatchesDAO matches;

    public static PlayersDAO players;
    public static GoalsDAO goals;
    public static RoundsDAO2 rounds;

    private static ORMLite ORMLite;

    private DB(){}

    public static void init(Context context){
        tournaments = new TournamentsDAO(context);
        tournaments.clean();
        teams = new TeamssDAO(context);
        teams.clean();
        matches = new MatchesDAO(context);
        matches.clean();

        ORMLite = OpenHelperManager.getHelper(context, ORMLite.class);
        ORMLite.clear();
        ORMLite.init();
        players = ORMLite.players;
        goals = ORMLite.goals;
        rounds = ORMLite.rounds;
    }

    public static void shutdown(){OpenHelperManager.releaseHelper();
    }
}
