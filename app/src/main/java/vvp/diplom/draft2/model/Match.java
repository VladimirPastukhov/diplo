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

    @JsonProperty("penalty1")
    private String penalty1;

    @JsonProperty("penalty2")
    private String penalty2;

    @JsonProperty("is_technical")
    boolean isTechnical;

    @JsonProperty("is_overtime")
    boolean isOvertime;

    @JsonProperty("referee")
    String referee;

    @JsonProperty("place")
    String place;

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

    public boolean isOvertime() {
        return isOvertime;
    }

    public void setIsOvertime(boolean isOvertime) {
        this.isOvertime = isOvertime;
    }

    public boolean isTechnical() {
        return isTechnical;
    }

    public void setIsTechnical(boolean isTechnical) {
        this.isTechnical = isTechnical;
    }

    public String getPenalty1() {
        return penalty1;
    }

    public void setPenalty1(String penalty1) {
        this.penalty1 = penalty1;
    }

    public String getPenalty2() {
        return penalty2;
    }

    public void setPenalty2(String penalty2) {
        this.penalty2 = penalty2;
    }

    public String getReferee() {
        return referee;
    }

    public void setReferee(String referee) {
        this.referee = referee;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "Match{" +
                "goals1='" + goals1 + '\'' +
                ", id='" + id + '\'' +
                ", startAt='" + startAt + '\'' +
                ", goals2='" + goals2 + '\'' +
                ", team1=" + team1 +
                ", team2=" + team2 +
                '}';
    }

    //================================ next methods for pass between activities ======================
    public Match(){}

    public Match(Match source) {
        this.goals1 = source.goals1;
        this.goals2 = source.goals2;
        this.id = source.id;
        this.startAt = source.startAt;
        this.team1 = source.team1;
        this.team2 = source.team2;
        this.penalty1 = source.penalty1;
        this.penalty2 = source.penalty2;
        this.isTechnical = source.isTechnical();
        this.isOvertime = source.isOvertime();
        this.referee = source.referee;
        this.place = source.place;
    }

    public Match(Parcel source){
        setId(source.readString());
        setStartAt(source.readString());
        setGoals1(source.readString());
        setGoals2(source.readString());
        setTeam1(source.<Team>readParcelable(Team.class.getClassLoader()));
        setTeam2(source.<Team>readParcelable(Team.class.getClassLoader()));
        setPenalty1(source.readString());
        setPenalty2(source.readString());
        setIsTechnical(source.readByte() != 0);
        setIsOvertime(source.readByte() != 0);
        setReferee(source.readString());
        setPlace(source.readString());
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
        dest.writeString(getPenalty1());
        dest.writeString(getPenalty2());
        dest.writeByte((byte) (isTechnical() ? 1 : 0));
        dest.writeByte((byte) (isOvertime() ? 1 : 0));
        dest.writeString(getReferee());
        dest.writeString(getPlace());
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
