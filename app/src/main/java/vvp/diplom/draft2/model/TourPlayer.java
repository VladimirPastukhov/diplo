package vvp.diplom.draft2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import vvp.diplom.draft2.db.dao.TourPlayersDao;

/**
 * Created by VoVqa on 23.05.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@DatabaseTable(tableName = "tournament_players")
public class TourPlayer {

    @JsonProperty("id")
    @DatabaseField(columnName = TourPlayersDao.ID)
    private String id;

    @JsonProperty("tournament_id")
    @DatabaseField(columnName = TourPlayersDao.TOURNAMENT_ID)
    private String tournamentId;

    @JsonProperty("team_id")
    @DatabaseField(columnName = TourPlayersDao.TEAM_ID)
    private String teamId;

    @JsonProperty("member_id")
    @DatabaseField(columnName = TourPlayersDao.PLAYER_ID)
    private String playerId;

    @JsonProperty("player")
    private Player player;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    @Override
    public String toString() {
        return "TourPlayer{" +
                "id='" + id + '\'' +
                ", tournamentId='" + tournamentId + '\'' +
                ", teamId='" + teamId + '\'' +
                ", playerId='" + playerId + '\'' +
                ", player=" + player +
                '}';
    }
}
