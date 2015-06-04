package vvp.diplom.draft2.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import vvp.diplom.draft2.db.dao.MatchesDao;

/**
 * Created by VoVqa on 15.05.2015.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@DatabaseTable(tableName = MatchesDao.TABLE_NAME)
public class Match implements Parcelable{
    @JsonProperty("id")
    @DatabaseField(id = true, columnName = MatchesDao.ID)
    private String id;

    @JsonProperty("round_id")
    @DatabaseField(columnName = MatchesDao.ROUND_ID)
    private String roundId;

    @JsonProperty("team1_id")
    @DatabaseField(columnName = MatchesDao.TEAM1_ID)
    private String team1Id;

    @JsonProperty("team2_id")
    @DatabaseField(columnName = MatchesDao.TEAM2_ID)
    private String team2Id;

    @JsonProperty("start_at")
    @DatabaseField(columnName = MatchesDao.START_AT)
    private String startAt;

    @JsonProperty("goals1")
    @DatabaseField(columnName = MatchesDao.GOALS1)
    private String goals1;

    @JsonProperty("goals2")
    @DatabaseField(columnName = MatchesDao.GOALS2)
    private String goals2;

    @JsonProperty("team1")
    Team team1;

    @JsonProperty("team2")
    Team team2;

    @JsonProperty("penalty1")
    @DatabaseField(columnName = MatchesDao.PENALTY1)
    private String penalty1;

    @JsonProperty("penalty2")
    @DatabaseField(columnName = MatchesDao.PENALTY2)
    private String penalty2;

    @JsonProperty("is_technical")
    @DatabaseField(columnName = MatchesDao.IS_TECHNICAL)
    private boolean isTechnical;

    @JsonProperty("is_overtime")
    @DatabaseField(columnName = MatchesDao.IS_OVERTIME)
    private boolean isOvertime;

    @JsonProperty("referee")
    @DatabaseField(columnName = MatchesDao.REFEREE)
    private String referee;

    @JsonProperty("place")
    @DatabaseField(columnName = MatchesDao.PLACE)
    private String place;

    private Round round;

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

    public String getRoundId() {
        return roundId;
    }

    public void setRoundId(String roundId) {
        this.roundId = roundId;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public String getTeam1Id() {
        return team1Id;
    }

    public void setTeam1Id(String team1Id) {
        this.team1Id = team1Id;
    }

    public String getTeam2Id() {
        return team2Id;
    }

    public void setTeam2Id(String team2Id) {
        this.team2Id = team2Id;
    }

    @Override
    public String toString() {
        return "Match{" +
                "goals1='" + goals1 + '\'' +
                ", id='" + id + '\'' +
                ", roundId='" + roundId + '\'' +
                ", startAt='" + startAt + '\'' +
                ", goals2='" + goals2 + '\'' +
                ", team1=" + team1 +
                ", team2=" + team2 +
                ", penalty1='" + penalty1 + '\'' +
                ", penalty2='" + penalty2 + '\'' +
                ", isTechnical=" + isTechnical +
                ", isOvertime=" + isOvertime +
                ", referee='" + referee + '\'' +
                ", place='" + place + '\'' +
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
        setRoundId(source.readString());
        setStartAt(source.readString());
        setGoals1(source.readString());
        setGoals2(source.readString());
        setTeam1Id(source.readString());
        setTeam2Id(source.readString());
        setTeam1(source.<Team>readParcelable(Team.class.getClassLoader()));
        setTeam2(source.<Team>readParcelable(Team.class.getClassLoader()));
        setPenalty1(source.readString());
        setPenalty2(source.readString());
        setIsTechnical(source.readByte() != 0);
        setIsOvertime(source.readByte() != 0);
        setReferee(source.readString());
        setPlace(source.readString());
        setRound(source.<Round>readParcelable(Round.class.getClassLoader()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getId());
        dest.writeString(getRoundId());
        dest.writeString(getStartAt());
        dest.writeString(getGoals1());
        dest.writeString(getGoals2());
        dest.writeString(getTeam1Id());
        dest.writeString(getTeam2Id());
        dest.writeParcelable(getTeam1(), 0);
        dest.writeParcelable(getTeam2(), 0);
        dest.writeString(getPenalty1());
        dest.writeString(getPenalty2());
        dest.writeByte((byte) (isTechnical() ? 1 : 0));
        dest.writeByte((byte) (isOvertime() ? 1 : 0));
        dest.writeString(getReferee());
        dest.writeString(getPlace());
        dest.writeParcelable(getRound(), 0);
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
