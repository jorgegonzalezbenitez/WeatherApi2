package org.example.model;


import java.time.Instant;
import java.util.List;

public class Hotel {
    private final List<Rate> rates;
    private final Date date;
    private  final String ss;
    private final Instant ts;



    public Hotel(List<Rate> rates, Date date) {
        this.rates = rates;
        this.date = date;
        this.ss = "hotel-provider";
        this.ts = Instant.now();
    }


    public List<Rate> getRates() {
        return rates;
    }

    public Date getDate() {
        return date;
    }

    public String getSs() {
        return ss;
    }


    public Instant getTs() {
        return ts;
    }
}
