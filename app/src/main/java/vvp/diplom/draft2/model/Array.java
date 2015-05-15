package vvp.diplom.draft2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by VoVqa on 13.05.2015.
 */
public class Array<T> {

    @JsonProperty("rows")
    private List<T> rows;

    @JsonProperty("total")
    private long total;

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
