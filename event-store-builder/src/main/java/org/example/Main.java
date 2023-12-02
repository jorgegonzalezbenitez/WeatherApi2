package org.example;

import org.example.control.JMSWeatherStore;
import org.example.control.WeatherSend;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private static String topicName = "prediction.Weather";
    private static String clientId = "Jorge";
    public static void main(String[] args) {
        JMRWeatherStore weatherReceiver = new JMRWeatherStore(args[0], topicName,clientId);
        weatherReceiver.receiveBroker();
    }
}