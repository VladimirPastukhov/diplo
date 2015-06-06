package vvp.diplom.draft2.model;

import java.util.List;

/**
 * Created by VoVqa on 05.06.2015.
 */
public class Protocol {

    private Match match;
    private List<MatchPlayer> matchPlayers;

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public List<MatchPlayer> getMatchPlayers() {
        return matchPlayers;
    }

    public void setMatchPlayers(List<MatchPlayer> matchPlayers) {
        this.matchPlayers = matchPlayers;
    }
}
