package vvp.diplom.draft2.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by VoVqa on 20.05.2015.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchPlayer implements Parcelable{
    @JsonProperty("id")
    private String id;

    @JsonProperty("player")
    private Player player;

    @JsonProperty("team")
    private Team team;

    @JsonProperty("teamsheet")
    private int status;

    @JsonProperty("is_capitan")
    private boolean isCaptain;

    @JsonProperty("is_goalkeeper")
    private boolean isGoalkeeper;

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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "MatchPlayer{" +
                "id='" + id + '\'' +
                ", player=" + player +
                ", team=" + team +
                ", status=" + status +
                ", isCaptain=" + isCaptain +
                ", isGoalkeeper=" + isGoalkeeper +
                '}';
    }

    //================================ next methods for pass between activities ======================
    public MatchPlayer(){}

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
