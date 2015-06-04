package vvp.diplom.draft2.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import vvp.diplom.draft2.db.dao.TeamsDao;

/**
 * Created by VoVqa on 15.05.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@DatabaseTable(tableName = TeamsDao.TABLE_NAME)
public class Team implements Parcelable{

    @JsonProperty("id")
    @DatabaseField(id = true, columnName = TeamsDao.ID)
    String id;

    @JsonProperty("title")
    @DatabaseField(columnName = TeamsDao.TITLE)
    String title;

    @JsonProperty("image_path")
    @DatabaseField(columnName = TeamsDao.IMAGE_PATH)
    String imagePath;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagetPath) {
        this.imagePath = imagetPath;
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
                ", imagetPath='" + imagePath + '\'' +
                '}';
    }

    //================================ next methods for pass between activities ======================
    public Team(){}

    public Team(Parcel source){
        setId(source.readString());
        setTitle(source.readString());
        setImagePath(source.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(getTitle());
        dest.writeString(getImagePath());
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
