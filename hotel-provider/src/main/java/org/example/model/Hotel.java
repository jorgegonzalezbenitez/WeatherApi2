package org.example.model;

import javax.xml.stream.Location;
import java.time.Instant;
import java.time.LocalDate;

public class Hotel {
    private final String code;
    private final  String rateName;
    private final int rate;
    private final int tax;
    private final Date date;

    private  final String ss;
    private  final Instant timeStamp;
    private final Instant ts;



    public Hotel(String code, String rateName, int rate, int tax,Instant timeStamp, Date date) {
        this.code = code;
        this.rateName = rateName;
        this.rate = rate;
        this.tax = tax;
        this.timeStamp = timeStamp;
        this.date = date;
        this.ss = "hotel-provider";
        this.ts = Instant.now();
    }



    public String getCode() {
        return code;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public String getRateName() {
        return rateName;
    }

    public int getRate() {
        return rate;
    }

    public int getTax() {
        return tax;
    }



    public String getSs() {
        return ss;
    }



    public Date getDate() {
        return date;
    }






}
