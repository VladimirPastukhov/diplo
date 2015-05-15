package vvp.diplom.draft2.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by VoVqa on 15.05.2015.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Match implements Parcelable{
    @JsonProperty("id")
    private String id;

    @JsonProperty("start_at")
    private String startAt;

    @JsonProperty("goals1")
    private String goals1;

    @JsonProperty("goals2")
    private String goals2;

    @JsonProperty("team1")
    Team team1;

    @JsonProperty("team2")
    Team team2;

    public String getGoals1() {
        return goals1;
    }

    public void setGoals1(String goals1) {
        this.goals1 = goals1;
    }

    public String getGoals2() {
        return goals2;
    }

    public void setGoals2(String goals2) {
        this.goals2 = goals2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    //================================ next methods for pass between activities ======================
    public Match(){}

    public Match(Parcel source){
        setId(source.readString());
        setStartAt(source.readString());
        setGoals1(source.readString());
        setGoals2(source.readString());
        setTeam1(source.<Team>readParcelable(Team.class.getClassLoader()));
        setTeam2(source.<Team>readParcelable(Team.class.getClassLoader()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getId());
        dest.writeString(getStartAt());
        dest.writeString(getGoals1());
        dest.writeString(getGoals2());
        dest.writeParcelable(getTeam1(), 0);
        dest.writeParcelable(getTeam2(), 0);
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
