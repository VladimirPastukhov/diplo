package vvp.diplom.draft2.db;

import android.content.Context;

/**
 * Created by VoVqa on 23.05.2015.
 */
public class DB {
    public static final String NAME = "Sportspring.dp";
    public static final int VERSION = 1;

    public static TournamentsDAO tournaments;
    public static RoundsDAO rounds;

    private DB(){}

    public static void init(Context context){
        tournaments = new TournamentsDAO(context);
        tournaments.clean();
        rounds = new RoundsDAO(context);
        rounds.clean();
    }
}
