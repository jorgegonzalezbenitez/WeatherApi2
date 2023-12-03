package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

public class WeatherWriter implements WriteDirectory {
    @Override
    public void mkdir(List<String> events){
        if (events.isEmpty()) {
            System.err.println("Events list is empty. Nothing to write.");
            return;
        }

        String ts = Instant.now().toString().replace(":", "-");

        String directoryPath = "eventstore\\prediction.Weather\\" + "prediction-weather" + "\\" + ts;
        String filePath = directoryPath + "\\event-store.events";


        File directory = new File(directoryPath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Directory created: " + directory.getAbsolutePath());
            } else {
                System.out.println("Failed to create directory: " + directory.getAbsolutePath());
                return;
            }
        }

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, (JsonSerializer<Instant>) (src, typeOfSrc, context) ->
                        context.serialize(src.getEpochSecond()))
                .create();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String event : events) {
                String json = gson.toJson(event);
                writer.write(json);
                writer.newLine();
            }
            System.out.println("Event data written to: " + filePath);
        } catch (IOException e) {
            System.out.println("Failed to write event data to: " + filePath);
            e.printStackTrace();
        }
    }
}
