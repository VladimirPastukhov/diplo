package vvp.diplom.draft2.network;

import android.util.Log;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import vvp.diplom.draft2.activities.Util;
import vvp.diplom.draft2.model.Goal;
import vvp.diplom.draft2.model.Incident;
import vvp.diplom.draft2.model.MatchPlayer;
import vvp.diplom.draft2.model.TourPlayer;
import vvp.diplom.draft2.network.apiLists.ApiList;
import vvp.diplom.draft2.model.Match;
import vvp.diplom.draft2.network.apiLists.Goals;
import vvp.diplom.draft2.network.apiLists.Incidents;
import vvp.diplom.draft2.network.apiLists.MatchPlayers;
import vvp.diplom.draft2.network.apiLists.Matches;
import vvp.diplom.draft2.model.Round;
import vvp.diplom.draft2.network.apiLists.Rounds;
import vvp.diplom.draft2.network.apiLists.TourPlayers;
import vvp.diplom.draft2.network.apiLists.Tournaments;
import vvp.diplom.draft2.model.LoginRequestAnswer;
import vvp.diplom.draft2.model.Tournament;

/**
 * Created by VoVqa on 12.05.2015.
 */
public class Network {

    private static final String TAG = Util.BASE_TAG + "Network";

    private static LoginRequestAnswer loginRequestAnswer;

    public static LoginRequestAnswer login(String login, String password){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("login", login);
        body.add("pass", password);
        body.add("client_id", API.CLIENT_ID);
        body.add("client_secret", API.CLIENT_SECREST);
        body.add("redirect_uri", API.REDIRECT_URI);

        HttpEntity httpEntity = new HttpEntity(body,headers);

        RestTemplate restTemplate = new RestTemplate();
        String url = API.BASE_URL + API.OAUTH_DIRECT;
        ResponseEntity<LoginRequestAnswer> answer = restTemplate.exchange(url, HttpMethod.POST, httpEntity, LoginRequestAnswer.class);
        loginRequestAnswer = answer.getBody();
        Log.d(TAG, loginRequestAnswer.toString());
        return loginRequestAnswer;
    }

    public static void patchMatch(Match match){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<Match> httpEntity = new HttpEntity(match, headers);

        String accessToken = loginRequestAnswer.getAccessToken();
        RestTemplate restTemplate = new RestTemplate();
        String url = API.BASE_URL + API.PATCH_MATCH + "?access_token=" + accessToken;

        Log.d(TAG, "PATCH "+match);
        restTemplate.exchange(url, HttpMethod.PATCH, httpEntity, String.class, match.getId());
    }


    public void postGoals(List<Goal> goals){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String accessToken = loginRequestAnswer.getAccessToken();
        RestTemplate restTemplate = new RestTemplate();
        String url = API.BASE_URL + API.POST_GOALS + "?access_token="+loginRequestAnswer.getAccessToken();

//        restTemplate.postForObject(url, Goal[].class, )

    }

    public static List<Tournament> loadMyTournaments(){
        String accessToken = loginRequestAnswer.getAccessToken();
        RestTemplate restTemplate = new RestTemplate();
        String url = API.BASE_URL + API.MY_TOURNAMENTS + "?access_token="+loginRequestAnswer.getAccessToken();
        Tournaments tournaments = restTemplate.getForObject(url, Tournaments.class);
        Log.d(TAG, "Tournaments loaded " + tournaments);
        return getNotNullData(tournaments);
    }

    public static List<Round> loadRounds(String tournamentId){
        String accessToken = loginRequestAnswer.getAccessToken();
        RestTemplate restTemplate = new RestTemplate();
        String url = API.BASE_URL + API.ROUNDS_BY_TOURNAMENT_ID + "?access_token="+loginRequestAnswer.getAccessToken();
        Rounds rounds = restTemplate.getForObject(url, Rounds.class, tournamentId);
        Log.d(TAG, "Rounds loaded " + rounds);
        return getNotNullData(rounds);
    }

    public static List<Match> loadMatches(String roundId){
        String accessToken = loginRequestAnswer.getAccessToken();
        RestTemplate restTemplate = new RestTemplate();
        String url = API.BASE_URL + API.MATCHES_BY_ROUND_ID + "?access_token="+loginRequestAnswer.getAccessToken();
        Matches matches = restTemplate.getForObject(url, Matches.class, roundId);
        Log.d(TAG, "Matches loaded " + matches);
        return getNotNullData(matches);
    }

    public static List<MatchPlayer> loadMatchPlayers(String matchId){
        String accessToken = loginRequestAnswer.getAccessToken();
        RestTemplate restTemplate = new RestTemplate();
        String url = API.BASE_URL + API.MATCH_PLAYERS_BY_MATCH_ID + "?access_token="+loginRequestAnswer.getAccessToken();
        MatchPlayers matchPlayers = restTemplate.getForObject(url, MatchPlayers.class, matchId);
        Log.d(TAG, "Match players loaded " + matchPlayers);

        return getNotNullData(matchPlayers);
    }

    public static List<Goal> loadGoals(String matchId){
        String accessToken = loginRequestAnswer.getAccessToken();
        RestTemplate restTemplate = new RestTemplate();
        String url = API.BASE_URL + API.GOALS_BY_MATCH_ID + "?access_token="+loginRequestAnswer.getAccessToken();
        Goals goals = restTemplate.getForObject(url, Goals.class, matchId);
        Log.d(TAG, "Goals loaded " + goals);
        return getNotNullData(goals);
    }

    public static List<Incident> loadIncidents(String matchId){
        String accessToken = loginRequestAnswer.getAccessToken();
        RestTemplate restTemplate = new RestTemplate();
        String url = API.BASE_URL + API.INCIDENTS_BY_MATCH_ID + "?access_token="+loginRequestAnswer.getAccessToken();
        Incidents incidents = restTemplate.getForObject(url, Incidents.class, matchId);
        Log.d(TAG, "Incidents loaded " + incidents);
        return getNotNullData(incidents);
    }

    public static List<TourPlayer> loadTourPlayers(String teamId, String tourId){
        String accessToken = loginRequestAnswer.getAccessToken();
        RestTemplate restTemplate = new RestTemplate();
        String url = API.BASE_URL + API.TOUR_PLAYERS_BY_TEAM_ID
                + "?access_token="+loginRequestAnswer.getAccessToken()
                + "&tournament_id="+tourId;
        TourPlayers tourPlayers = restTemplate.getForObject(url, TourPlayers.class, teamId);
        for(TourPlayer tourPlayer : getNotNullData(tourPlayers)){
            tourPlayer.setTournamentId(tourId);
        }
        Log.d(TAG, "Tour players loaded " + tourPlayers);
        return getNotNullData(tourPlayers);
    }

    private static <T> List<T> getNotNullData(ApiList<T> apiList) {
        List data = apiList.getData();
        if (data == null) {
            data = new ArrayList<T>(0);
        }
        return data;
    }

    //==============================================================================================

}
