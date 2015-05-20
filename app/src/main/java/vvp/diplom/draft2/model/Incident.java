package vvp.diplom.draft2.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by VoVqa on 20.05.2015.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Incident implements Parcelable{

    @JsonProperty("id")
    String id;

    @JsonProperty("match_id")
    String matchId;

    @JsonProperty("note")
    String note;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Incident{" +
                "id='" + id + '\'' +
                ", matchId='" + matchId + '\'' +
                ", note='" + note + '\'' +
                '}';
    }


    //================================ next methods for pass between activities ======================
    public Incident(){}

    public Incident(Parcel source){
        setId(source.readString());
        setMatchId(source.readString());
        setNote(source.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getId());
        dest.writeString(getMatchId());
        dest.writeString(getNote());
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Object createFromParcel(Parcel source) {
            return new Incident(source);
        }

        @Override
        public Incident[] newArray(int size) {
            return new Incident[size];
        }
    };
}
