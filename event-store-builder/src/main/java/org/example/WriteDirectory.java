package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import org.example.model.Weather;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

class WriteDirectory {

    public  static void writeWeatherListToDirectory(List<Weather> weatherList) {
        if (weatherList.isEmpty()) {
            System.err.println("Weather list is empty. Nothing to write.");
            return;
        }

        Weather firstWeather = weatherList.get(0);
        String ts = firstWeather.getTs().toString().replace(":", "-");
        String directoryPath = "eventstore" + File.separator + "prediction.Weather" + File.separator + firstWeather.getSs() + File.separator + ts;

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Directory created: " + directory.getAbsolutePath());
            } else {
                System.out.println("Failed to create directory: " + directory.getAbsolutePath());
                return;
            }
        }

        String filePath = directoryPath + File.separator + "weather-data.events";
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, (JsonSerializer<Instant>) (src, typeOfSrc, context) ->
                        context.serialize(src.getEpochSecond()))
                .create();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Weather weather : weatherList) {
                String json = gson.toJson(weather);
                writer.write(json);
                writer.newLine();
            }
            System.out.println("Weather data written to: " + filePath);
        } catch (IOException e) {
            System.out.println("Failed to write weather data to: " + filePath);
            e.printStackTrace();
        }
    }
}