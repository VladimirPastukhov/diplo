package vvp.diplom.draft2.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import vvp.diplom.draft2.db.RoundsSQL;

/**
 * Created by VoVqa on 13.05.2015.
 */
@DatabaseTable(tableName = RoundsSQL.TABLE_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Round implements Parcelable{

    @JsonProperty("id")
    @DatabaseField(id = true, columnName = RoundsSQL.ID)
    String id;

    @JsonProperty("tournament_id")
    @DatabaseField(columnName = RoundsSQL.TOURNAMENT_ID)
    String tournamentId;

    @JsonProperty("name")
    @DatabaseField(dataType = DataType.STRING, columnName = RoundsSQL.NAME)
    String name;

    Tournament tournament;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    @Override
    public String toString() {
        return "Round{" +
                "id='" + id + '\'' +
                ", tournamentId='" + tournamentId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    //================================ next methods for pass between activities ======================
    public Round(){}

    public Round(Parcel source){
        setId(source.readString());
        setTournamentId(source.readString());
        setName(source.readString());
        setTournament(source.<Tournament>readParcelable(Tournament.class.getClassLoader()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getId());
        dest.writeString(getTournamentId());
        dest.writeString(getName());
        dest.writeParcelable(getTournament(), 0);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Object createFromParcel(Parcel source) {
            return new Round(source);
        }

        @Override
        public Round[] newArray(int size) {
            return new Round[size];
        }
    };
}
