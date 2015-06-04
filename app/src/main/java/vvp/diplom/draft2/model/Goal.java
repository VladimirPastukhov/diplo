package vvp.diplom.draft2.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import vvp.diplom.draft2.db.dao.GoalsDao;

/**
 * Created by VoVqa on 20.05.2015.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@DatabaseTable(tableName = GoalsDao.TABLE_NAME)
public class Goal implements Parcelable {

    @JsonProperty("id")
    @DatabaseField(id = true, columnName = GoalsDao.ID)
    private String id;

    @JsonProperty("match_id")
    @DatabaseField(columnName = GoalsDao.MATCH_ID)
    private String matchId;

    @JsonProperty("team_id")
    @DatabaseField(columnName = GoalsDao.TEAM_ID)
    private String teamId;

    @JsonProperty("player_id")
    @DatabaseField(columnName = GoalsDao.PLAYER_ID)
    private String playerId;

    @JsonProperty("assistant_id")
    @DatabaseField(columnName = GoalsDao.ASSISTANT_ID)
    private String assistantId;

    @JsonProperty("team")
    Team team;

    @JsonProperty("player")
    Player player;

    @JsonProperty("minute")
    @DatabaseField(columnName = GoalsDao.MINUTE)
    private String minute;

    @JsonProperty("additional_minute")
    @DatabaseField(columnName = GoalsDao.ADDITIONAL_MINUTE)
    private String additionalMinute;

    @JsonProperty("is_penalty")
    @DatabaseField(columnName = GoalsDao.IS_PENALTY)
    private boolean isPenalty;

    @JsonProperty("is_autogoal")
    @DatabaseField(columnName = GoalsDao.IS_AUTOGOAL)
    private boolean isAutogoal;

    public String getAdditionalMinute() {
        return additionalMinute;
    }

    public void setAdditionalMinute(String additionalMinute) {
        this.additionalMinute = additionalMinute;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAutogoal() {
        return isAutogoal;
    }

    public void setIsAutogoal(boolean isAutogoal) {
        this.isAutogoal = isAutogoal;
    }

    public boolean isPenalty() {
        return isPenalty;
    }

    public void setIsPenalty(boolean isPenalty) {
        this.isPenalty = isPenalty;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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

    public String getAssistantId() {
        return assistantId;
    }

    public void setAssistantId(String assistantId) {
        this.assistantId = assistantId;
    }

    @Override
    public String toString() {
        return "Goal{" +
                "additionalMinute='" + additionalMinute + '\'' +
                ", id='" + id + '\'' +
                ", matchId='" + matchId + '\'' +
                ", teamId='" + teamId + '\'' +
                ", playerId='" + playerId + '\'' +
                ", team=" + team +
                ", player=" + player +
                ", minute='" + minute + '\'' +
                ", isPenalty=" + isPenalty +
                ", isAutogoal=" + isAutogoal +
                '}';
    }

    //================================ next methods for pass between activities ======================
    public Goal(){}

    public Goal(Parcel source){
        setId(source.readString());
        setMatchId(source.readString());
        setTeamId(source.readString());
        setPlayerId(source.readString());
        setAssistantId(source.readString());
        setTeam(source.<Team>readParcelable(Team.class.getClassLoader()));
        setPlayer(source.<Player>readParcelable(Player.class.getClassLoader()));
        setMinute(source.readString());
        setAdditionalMinute(source.readString());
        setIsPenalty(source.readByte() != 0);
        setIsAutogoal(source.readByte() != 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getId());
        dest.writeString(getMatchId());
        dest.writeString(getTeamId());
        dest.writeString(getPlayerId());
        dest.writeString(getAssistantId());
        dest.writeParcelable(getTeam(), 0);
        dest.writeParcelable(getPlayer(), 0);
        dest.writeString(getMinute());
        dest.writeString(getAdditionalMinute());
        dest.writeByte((byte) (isPenalty() ? 1 : 0));
        dest.writeByte((byte) (isAutogoal() ? 1 : 0));
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Object createFromParcel(Parcel source) {
            return new Goal(source);
        }

        @Override
        public Goal[] newArray(int size) {
            return new Goal[size];
        }
    };
}
