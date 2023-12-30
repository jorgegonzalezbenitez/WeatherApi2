package org.example.control;

import java.util.Timer;
import java.util.TimerTask;

public class WeatherMain {
    private static String topicName = "prediction.Weather";


    public static void main(String[] args) {
        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                WeatherProvider weatherProvider = new WeatherMapProvider(args[0]);
                WeatherSend weatherSend = new JMSWeatherStore(args[1], topicName);
                WeatherController weatherController = new WeatherController(weatherProvider,weatherSend);
                weatherController.execute();
            }
        };

        long time_of_execution = 21600 * 1000;
        timer.schedule(timerTask, 0, time_of_execution);
    }



}