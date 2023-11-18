package org.example.control;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.example.model.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
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