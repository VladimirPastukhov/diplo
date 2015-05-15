package vvp.diplom.draft2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by VoVqa on 13.05.2015.
 */
public class ApiList<T> {

    @JsonProperty("rows")
    private List<T> data;

    @JsonProperty("total")
    private long total;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ApiList{" +
                "total=" + total +
                ", data=" + data +
                '}';
    }
}
