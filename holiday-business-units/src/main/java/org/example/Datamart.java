package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.*;

public class Datamart implements Listener{


    @Override
    public void weatherStore(String message) {
        try {
            JsonObject jsonObject = new Gson().fromJson(message, JsonObject.class);
            JsonObject location = jsonObject.getAsJsonObject("location");
            String name = location.getAsJsonPrimitive("name").getAsString();
            String cloud = jsonObject.getAsJsonPrimitive("cloud").getAsString();
            String windSpeed = jsonObject.getAsJsonPrimitive("windSpeed").getAsString();
            String temp = jsonObject.getAsJsonPrimitive("temp").getAsString();
            String humidity = jsonObject.getAsJsonPrimitive("humidity").getAsString();
            String instant = jsonObject.getAsJsonPrimitive("instant").getAsString();
            String propRain = jsonObject.getAsJsonPrimitive("propRain").getAsString();
            String ts = jsonObject.getAsJsonPrimitive("ts").getAsString();
            String ss = jsonObject.getAsJsonPrimitive("ss").getAsString();
            String lat = location.getAsJsonPrimitive("lat").getAsString();
            String lon = location.getAsJsonPrimitive("lon").getAsString();

            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\cgsos\\Downloads\\DataBase\\weather.db")) {
                String tableName = name.toLowerCase().replace(" ", "_");
                String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                        "cloud TEXT," +
                        "windSpeed TEXT," +
                        "temp TEXT," +
                        "humidity TEXT," +
                        "instant TEXT," +
                        "propRain TEXT," +
                        "ts TEXT," +
                        "ss TEXT," +
                        "lat TEXT," +
                        "lon TEXT" +
                        ")";

                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(createTableSQL);

                    String insertWeatherSQL = "INSERT INTO " + tableName + " ( cloud, windSpeed, temp, humidity, instant, propRain, ts, ss, lat, lon) " +
                            "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertWeatherSQL)) {
                        insertStatement.setString(1, cloud);
                        insertStatement.setString(2, windSpeed);
                        insertStatement.setString(3, temp);
                        insertStatement.setString(4, humidity);
                        insertStatement.setString(5, instant);
                        insertStatement.setString(6, propRain);
                        insertStatement.setString(7, ts);
                        insertStatement.setString(8, ss);
                        insertStatement.setString(9, lat);
                        insertStatement.setString(10, lon);

                        insertStatement.executeUpdate();
                    }
                }

            } catch (SQLException | NullPointerException exc) {
                exc.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }System.out.println("Weather database created");
    }

    @Override
    public void hotelStore(String message) {
        try {
            JsonObject jsonObject = new Gson().fromJson(message, JsonObject.class);
            JsonArray ratesArray = jsonObject.getAsJsonArray("rates");
            JsonObject dateObject = jsonObject.getAsJsonObject("date");
            String country = dateObject.getAsJsonPrimitive("country").getAsString();

            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\cgsos\\Downloads\\DataBase\\hotel.db")) {
                String tableName = country.toLowerCase().replace(" ", "_");
                String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                        "name TEXT," +
                        "hotelKey TEXT," +
                        "checkIn TEXT," +
                        "checkOut TEXT," +
                        "ss TEXT," +
                        "ts TEXT," +
                        "code TEXT," +
                        "rateName TEXT," +
                        "rate INTEGER," +
                        "tax INTEGER" +
                        ")";

                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(createTableSQL);

                    for (JsonElement rateElement : ratesArray) {
                        JsonObject rateObject = rateElement.getAsJsonObject();
                        String code = rateObject.getAsJsonPrimitive("code").getAsString();
                        String rateName = rateObject.getAsJsonPrimitive("rateName").getAsString();
                        String rate = rateObject.getAsJsonPrimitive("rate").getAsString();
                        String tax = rateObject.getAsJsonPrimitive("tax").getAsString();

                        String insertRateSQL = "INSERT INTO " + tableName + " (name, hotelKey, checkIn, checkOut, ss, ts, code, rateName, rate, tax) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement insertStatement = connection.prepareStatement(insertRateSQL)) {
                            insertStatement.setString(1, dateObject.getAsJsonPrimitive("name").getAsString());
                            insertStatement.setString(2, dateObject.getAsJsonPrimitive("hotelKey").getAsString());
                            insertStatement.setString(3, dateObject.getAsJsonPrimitive("checkIn").getAsString());
                            insertStatement.setString(4, dateObject.getAsJsonPrimitive("checkOut").getAsString());
                            insertStatement.setString(5, jsonObject.getAsJsonPrimitive("ss").getAsString());
                            insertStatement.setString(6, jsonObject.getAsJsonPrimitive("ts").getAsString());
                            insertStatement.setString(7, code);
                            insertStatement.setString(8, rateName);
                            insertStatement.setString(9, rate);
                            insertStatement.setString(10, tax);

                            insertStatement.executeUpdate();
                        }
                    }
                }
                System.out.println("Hotel database created");
            } catch (SQLException | NullPointerException exc) {
                exc.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

