package vvp.diplom.draft2.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by VoVqa on 20.05.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Goal implements Parcelable {

    @JsonProperty("id")
    private String id;

    @JsonProperty("team")
    Team team;

    @JsonProperty("player")
    Player player;

    @JsonProperty("minute")
    private String minute;

    @JsonProperty("additional_minute")
    private String additionalMinute;

    @JsonProperty("is_penalty")
    private boolean isPenalty;

    @JsonProperty("is_autogoal")
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

    @Override
    public String toString() {
        return "Goal{" +
                "additionalMinute='" + additionalMinute + '\'' +
                ", id='" + id + '\'' +
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
