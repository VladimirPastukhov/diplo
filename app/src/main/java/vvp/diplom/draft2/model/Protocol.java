package vvp.diplom.draft2.model;

import java.util.List;

/**
 * Created by VoVqa on 05.06.2015.
 */
public class Protocol {
    private Match match;
    private List<MatchPlayer> matchPlayers;
    private List<TourPlayer> tourPlayers;
    private List<Goal> goals;

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

    public List<MatchPlayer> getMatchPlayers() {
        return matchPlayers;
    }

    public void setMatchPlayer(List<MatchPlayer> matchPlayers) {
        this.matchPlayers = matchPlayers;
    }

    public List<TourPlayer> getTourPlayers() {
        return tourPlayers;
    }

    public void setTourPlayers(List<TourPlayer> tourPlayers) {
        this.tourPlayers = tourPlayers;
    }
}
