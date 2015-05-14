package vvp.diplom.draft2;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by VoVqa on 12.05.2015.
 */
public class Greeting {


    private String id;

    @JsonProperty("content")
    private String contenta;

    public String getId() {
        return this.id;
    }

    public String getContenta() {
        return this.contenta;
    }

}
