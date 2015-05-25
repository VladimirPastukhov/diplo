package vvp.diplom.draft2.network;

/**
 * Created by VoVqa on 12.05.2015.
 */
public class API {

    public static final String BASE_URL = "http://api.sportand.me:8000";

    public static final String OAUTH_DIRECT = "/oauth/direct";
    public static final String TOURNAMENT = "/tournament/{id}";
    public static final String MY_TOURNAMENTS = "/user/me/tournament";
    public static final String ROUNDS_BY_TOURNAMENT_ID = "/tournament/{tournament_id}/round";
    public static final String MATCHES_BY_ROUND_ID = "/round/{round_id}/match";
    public static final String MATCH_BY_ID = "/match/{match_id}";
    public static final String MATCH_PLAYERS_BY_MATCH_ID = "/match/{match_id}/match_player";
    public static final String GOALS_BY_MATCH_ID = "/match/{match_id}/goal";
    public static final String INCIDENTS_BY_MATCH_ID = "/match/{match_id}/incident";
    public static final String TOUR_PLAYERS_BY_TEAM_ID = "/team/{team_id}/applicant";

    public static final String POST_GOALS = "/goal";

    public static final String PATCH_MATCH = "/match/{match_id}";

    public static final String CLIENT_ID = "6";
    public static final String CLIENT_SECREST = "4d3bc048b6f847ght9d5vf9fbc8q0m8x";
    public static final String REDIRECT_URI = "samapi://success";
}
