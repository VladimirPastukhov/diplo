package vvp.diplom.draft2.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import vvp.diplom.draft2.db.dao.MatchPlayersDao;

/**
 * Created by VoVqa on 20.05.2015.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@DatabaseTable(tableName = MatchPlayersDao.TABLE_NAME)
public class MatchPlayer implements Parcelable{

    public static final int STATUS_NOT_APPLIED = -1;
    public static final int STATUS_APPLIED = 0;
    public static final int STATUS_APPLIED_MAIN = 1;
    public static final int STATUS_APPLIED_RESERVE = 2;
    public static final int STATUS_APPLIED_COACH = 4;


    @JsonProperty("id")
    @DatabaseField(id = true, columnName = MatchPlayersDao.ID)
    private String id;

    @JsonProperty("match_id")
    @DatabaseField(columnName = MatchPlayersDao.MATCH_ID)
    private String matchId;

    @JsonProperty("team_id")
    @DatabaseField(columnName = MatchPlayersDao.TEAM_ID)
    private String teamId;

    @JsonProperty("player_id")
    @DatabaseField(columnName = MatchPlayersDao.PLAYER_ID)
    private String playerId;

    @JsonProperty("teamsheet")
    @DatabaseField(columnName = MatchPlayersDao.STATUS)
    private int status = STATUS_NOT_APPLIED;

    @JsonProperty("is_captain")
    @DatabaseField(columnName = MatchPlayersDao.IS_CAPTAIN)
    private boolean isCaptain;

    @JsonProperty("is_goalkeeper")
    @DatabaseField(columnName = MatchPlayersDao.IS_GOALKEEPER)
    private boolean isGoalkeeper;

    @JsonProperty("team")
    private Team team;

    @JsonProperty("player")
    private Player player;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isCaptain() {
        return isCaptain;
    }

    public void setIsCaptain(boolean isCaptain) {
        this.isCaptain = isCaptain;
    }

    public boolean isGoalkeeper() {
        return isGoalkeeper;
    }

    public void setIsGoalkeeper(boolean isGoalkeeper) {
        this.isGoalkeeper = isGoalkeeper;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setStatusSimple(boolean isApplied) {
        setStatus(isApplied ? STATUS_APPLIED : STATUS_NOT_APPLIED);
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
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

    public boolean isActive(){
        return getStatus() > STATUS_NOT_APPLIED;
    }

    @Override
    public String toString() {
        return "MatchPlayer{" +
                "id='" + id + '\'' +
                ", matchId='" + matchId + '\'' +
                ", teamId='" + teamId + '\'' +
                ", playerId='" + playerId + '\'' +
                ", status=" + status +
                ", isCaptain=" + isCaptain +
                ", isGoalkeeper=" + isGoalkeeper +
                ", team=" + team +
                ", player=" + player +
                '}';
    }

    public void update(MatchPlayer other){
        setId(other.getId());
        setStatus(other.getStatus());
        setIsCaptain(other.isCaptain);
        setIsGoalkeeper(other.isGoalkeeper());
    }
    //================================ next methods for pass between activities ======================
    public MatchPlayer(){}

    public MatchPlayer(Player player, String teamId, String matchId){
        setPlayerId(player.getId());
        setPlayer(player);
        setMatchId(matchId);
        setTeamId(teamId);
    }

    public MatchPlayer(Parcel source){
        setId(source.readString());
        setPlayer(source.<Player>readParcelable(Player.class.getClassLoader()));
        setTeam(source.<Team>readParcelable(Team.class.getClassLoader()));
        setStatus(source.readInt());
        setIsCaptain(source.readByte() != 0);
        setIsGoalkeeper(source.readByte() != 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getId());
        dest.writeParcelable(getPlayer(), 0);
        dest.writeParcelable(getTeam(), 0);
        dest.writeInt(getStatus());
        dest.writeByte((byte) (isCaptain() ? 1 : 0));
        dest.writeByte((byte) (isGoalkeeper() ? 1 : 0));
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Object createFromParcel(Parcel source) {
            return new Match(source);
        }

        @Override
        public Match[] newArray(int size) {
            return new Match[size];
        }
    };
}
