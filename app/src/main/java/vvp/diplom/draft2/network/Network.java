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

import vvp.diplom.draft2.network.apiLists.ApiList;
import vvp.diplom.draft2.model.Match;
import vvp.diplom.draft2.network.apiLists.Matches;
import vvp.diplom.draft2.model.Round;
import vvp.diplom.draft2.network.apiLists.Rounds;
import vvp.diplom.draft2.network.apiLists.Tournaments;
import vvp.diplom.draft2.model.LoginRequestAnswer;
import vvp.diplom.draft2.model.Tournament;

/**
 * Created by VoVqa on 12.05.2015.
 */
public class Network {

    private static final String TAG = "Network";

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
        return loginRequestAnswer;
    }

    public static List<Tournament> loadMyTournaments(){
        String accessToken = loginRequestAnswer.getAccessToken();
        RestTemplate restTemplate = new RestTemplate();
        String url = API.BASE_URL + API.MY_TOURNAMENTS + "?access_token="+loginRequestAnswer.getAccessToken();
        Tournaments tournaments = restTemplate.getForObject(url, Tournaments.class);
        Log.d(TAG, "Received " + tournaments);
        return getNotNullData(tournaments);
    }

    public static List<Round> loadRounds(String tournamentId){
        String accessToken = loginRequestAnswer.getAccessToken();
        RestTemplate restTemplate = new RestTemplate();
        String url = API.BASE_URL + API.ROUNDS_BY_TOURNAMENT_ID + "?access_token="+loginRequestAnswer.getAccessToken();
        Rounds rounds = restTemplate.getForObject(url, Rounds.class, tournamentId);
        Log.d(TAG, "Received " + rounds);
        return getNotNullData(rounds);
    }

    public static List<Match> loadMatches(String roundId){
        String accessToken = loginRequestAnswer.getAccessToken();
        RestTemplate restTemplate = new RestTemplate();
        String url = API.BASE_URL + API.MATCHES_BY_ROUND_ID + "?access_token="+loginRequestAnswer.getAccessToken();
        Matches matches = restTemplate.getForObject(url, Matches.class, roundId);
        Log.d(TAG, "Received " + matches);
        return getNotNullData(matches);
    }

    private static <T> List<T> getNotNullData(ApiList<T> apiList) {
        List data = apiList.getData();
        if (data == null) {
            data = new ArrayList<T>(0);
        }
        return data;
    }
}
