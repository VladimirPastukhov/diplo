package vvp.diplom.draft2.model;

import java.util.ArrayList;
import java.util.List;

import vvp.diplom.draft2.activities.Exstras;
import vvp.diplom.draft2.db.DB;

/**
 * Created by VoVqa on 05.06.2015.
 */
public class Protocol {

    private Match match;
    private List<MatchPlayer> team1Players;
    private List<MatchPlayer> team2Players;
    private List<Goal> goals;

    private Protocol(){}

    public static Protocol loadFromDB(String matchId){
        Match match = DB.matches.getById(matchId);
        String team1Id = match.getTeam1().getId();
        String team2Id = match.getTeam2().getId();
        String tourId  = match.getRound().getTournamentId();

        Protocol protocol = new Protocol();
        protocol.setMatch(match);
        protocol.setTeam1Players(DB.matchPlayers.getExtendedList(tourId, matchId, team1Id));
        protocol.setTeam2Players(DB.matchPlayers.getExtendedList(tourId, matchId, team2Id));
        protocol.setGoals(DB.goals.getByMatchId(matchId));
        return protocol;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public List<MatchPlayer> getTeam1Players() {
        return team1Players;
    }

    public void setTeam1Players(List<MatchPlayer> team1Players) {
        this.team1Players = team1Players;
    }

    public List<MatchPlayer> getTeam2Players() {
        return team2Players;
    }

    public void setTeam2Players(List<MatchPlayer> team2Players) {
        this.team2Players = team2Players;
    }

    public List<MatchPlayer> getTeam1ActivePlayers(){
        return getActivePlayers(team1Players);
    }

    public List<MatchPlayer> getTeam2ActivePlayers(){
        return getActivePlayers(team2Players);
    }

    public List<MatchPlayer> getAllActivePlayers(){
        List<MatchPlayer> all = getTeam1ActivePlayers();
        all.addAll(getTeam2ActivePlayers());
        return all;
    }

    private static List<MatchPlayer> getActivePlayers(List<MatchPlayer> players){
        List<MatchPlayer> list = new ArrayList<>();
        for(MatchPlayer player : players){
            if(player.isActive())
                list.add(player);
        }
        return list;
    }
}
