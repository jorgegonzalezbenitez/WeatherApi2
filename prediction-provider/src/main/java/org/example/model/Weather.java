package org.example.model;

import java.time.Instant;
public class Weather {

    private final double temp;
    private final int humidity;
    private final int cloud;
    private final double windSpeed;
    private final Double propRain;
    private final Instant instant;
    private final   Instant ts ;
    private final   String ss;
    private Location location;

    public Weather(Location location, double temp, int humidity, int cloud, double windSpeed, Double propRain, Instant instant) {
        this.temp = temp;
        this.humidity = humidity;
        this.cloud = cloud;
        this.windSpeed = windSpeed;
        this.propRain = propRain;
        this.instant = instant;
        this.ts = Instant.now();
        this.ss = "prediction-provider";
        this.location = location;
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

    public double getWindSpeed() {
        return windSpeed;
    }

    public Double getPropRain() {
        return propRain;
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

