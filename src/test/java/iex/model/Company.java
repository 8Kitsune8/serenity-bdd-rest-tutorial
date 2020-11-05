package iex.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Company {

    private String name;
    private String bs;

    public Company(String name, String bs) {
        this.name = name;
        this.bs = bs;
    }

    public String getName() {
        return name;
    }

    public String getBs() {
        return bs;
    }
}
