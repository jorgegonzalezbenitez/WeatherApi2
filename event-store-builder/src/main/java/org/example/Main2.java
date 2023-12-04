package org.example;

import org.example.model.Weather;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main2 {
    private static String topicName = "prediction.Weather";
    private static String clientId = "Jorge";
    public static void main(String[] args) {
        WeatherReceive weatherReceive = new JMRWeatherStore(args[0], topicName, clientId);
        weatherReceive.receiveBroker();
    }
}