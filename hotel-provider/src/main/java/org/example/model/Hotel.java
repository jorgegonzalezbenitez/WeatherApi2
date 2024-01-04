package org.example.model;

import javax.xml.stream.Location;
import java.time.Instant;
import java.time.LocalDate;
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
