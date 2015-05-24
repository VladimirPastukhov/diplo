package vvp.diplom.draft2.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import vvp.diplom.draft2.db.PlayerSQL;
import vvp.diplom.draft2.db.PlayersDAO;

/**
 * Created by VoVqa on 20.05.2015.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@DatabaseTable(tableName = "players")
public class Player implements Parcelable{

    @JsonProperty("id")
    @DatabaseField(id = true, columnName = PlayersDAO.ID)
    private String id;

    @JsonProperty("full_name")
    @DatabaseField(columnName = PlayersDAO.NAME)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    //================================ next methods for pass between activities ======================
    public Player(){}

    public Player(Parcel source){
        setName(source.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getName());
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Object createFromParcel(Parcel source) {
            return new Player(source);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
}
