package vvp.diplom.draft2.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by VoVqa on 15.05.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Team implements Parcelable{

    @JsonProperty("id")
    String id;

    @JsonProperty("title")
    String title;

    @JsonProperty("image_path")
    String imagetPath;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagetPath() {
        return imagetPath;
    }

    public void setImagetPath(String imagetPath) {
        this.imagetPath = imagetPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", imagetPath='" + imagetPath + '\'' +
                '}';
    }

    //================================ next methods for pass between activities ======================
    public Team(){}

    public Team(Parcel source){
        setId(source.readString());
        setTitle(source.readString());
        setImagetPath(source.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(getTitle());
        dest.writeString(getImagetPath());
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Object createFromParcel(Parcel source) {
            return new Team(source);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };
}
