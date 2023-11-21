package org.example.control;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {
        Timer timer = new Timer();

        TimerTask TimerTask = new TimerTask() {
            @Override
            public void run() {
                WeatherControl weatherController = new WeatherControl(new WeatherMapProvider(args[0]));
                weatherController.execute();
            }
        };

        long execution_time = 6 * 60 * 60 * 1000;
        timer.schedule(TimerTask, 0, execution_time);
    }
}