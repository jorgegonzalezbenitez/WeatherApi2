package org.example.control;

import org.example.model.Weather;


public interface WeatherSend {
    void sendBroker(Weather weather);
}
