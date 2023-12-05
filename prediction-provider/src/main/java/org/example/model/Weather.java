package org.example.model;

import java.time.Instant;
public class Weather {

    private final double temp;
    private final int humidity;
    private final int cloud;
    private final double wind_speed;
    private final Double prop_rain;
    private final Instant instant;
    private final   Instant ts ;
    private final   String ss;

    public Weather(double temp, int humidity, int cloud, double wind_speed, Double prop_rain, Instant instant) {
        this.temp = temp;
        this.humidity = humidity;
        this.cloud = cloud;
        this.wind_speed = wind_speed;
        this.prop_rain = prop_rain;
        this.instant = instant;
        this.ts = Instant.now();
        this.ss = "prediction-provider";
    }

    public double getTemp() {
        return temp;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getCloud() {
        return cloud;
    }

    public double getWind_speed() {
        return wind_speed;
    }

    public Double getProp_rain() {
        return prop_rain;
    }

    public Instant getInstant() {
        return instant;
    }

    public Instant getTs() {
        return ts;
    }

    public String getSs() {
        return ss;
    }



}

