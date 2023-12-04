package org.example;

public class MainReceiver {
    private static String topicName = "prediction.Weather";
    private static String clientId = "Jorge González Benítez";
    public static void main(String[] args) {
        WeatherReceive weatherReceive = new JMRWeatherStore(args[0], topicName, clientId,args[1]);
        weatherReceive.receiveBroker();
    }
}