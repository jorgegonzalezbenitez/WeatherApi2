package org.example;

import org.example.model.Weather;

import java.util.List;

public interface WeatherReceive {
    List<Weather> receiveBroker();
}
