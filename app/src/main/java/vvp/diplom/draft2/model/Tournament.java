package vvp.diplom.draft2.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import vvp.diplom.draft2.db.dao.TournamentsDao;

/**
 * Created by VoVqa on 12.05.2015.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@DatabaseTable(tableName = TournamentsDao.TABLE_NAME)
public class Tournament implements Parcelable{

    @JsonProperty("tournament_id")
    @DatabaseField(id = true, columnName = TournamentsDao.ID)
    private String id;

    @JsonProperty("title")
    @DatabaseField(columnName = TournamentsDao.TITLE)
    private String title;

    @JsonProperty("startdate")
    @DatabaseField(columnName = TournamentsDao.START_DATE)
    private String startDate;

    @JsonProperty("enddate")
    @DatabaseField(columnName = TournamentsDao.END_DATE)
    private String endDate;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString(){
        return getClass().getSimpleName()+" "+id+" "+title+" "+startDate+" "+endDate;
    }

    //================================ next methods for pass between activities ======================
    public Tournament(){}

    public Tournament(Parcel source){
        setId(source.readString());
        setTitle(source.readString());
        setStartDate(source.readString());
        setEndDate(source.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getId());
        dest.writeString(getTitle());
        dest.writeString(getStartDate());
        dest.writeString(getEndDate());
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Object createFromParcel(Parcel source) {
            return new Tournament(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new Tournament[size];
        }
    };
}
