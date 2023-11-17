package org.example.model;
import java.time.Instant;
public class Weather {
    private final double temp;
    private final int humidity;
    private final int clouds;
    private final double speed;
    private final Double pop;
    private final Instant instant;

    public Weather(double temp, int humidity, int clouds, double speed, Double pop, Instant instant) {
        this.temp = temp;
        this.humidity = humidity;
        this.clouds = clouds;
        this.speed = speed;
        this.pop = pop;
        this.instant = instant;
    }

    public double getTemp() {
        return temp;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getClouds() {
        return clouds;
    }

    public double getSpeed() {
        return speed;
    }

    public Double getPop() {
        return pop;
    }



    public Instant getInstant() {
        return instant;
    }
}

