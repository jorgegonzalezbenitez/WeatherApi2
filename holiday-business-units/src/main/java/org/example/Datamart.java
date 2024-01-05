package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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

            LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.parse(ts), ZoneId.systemDefault());
            String ts1 = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd/HH:mm"));

            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\cgsos\\Downloads\\DataBase\\weather.db")) {
                String tableName = name.toLowerCase().replaceAll(" ", "_");
                try {
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
                        System.out.println("Created table for: " + tableName);
                    }
                } catch (SQLException exc) {
                    exc.printStackTrace();
                }

                // Iniciar una transacción
                connection.setAutoCommit(false);

                try {
                    // Eliminar filas con ts diferente al nuevo ts
                    String deleteRowsSQL = "DELETE FROM " + tableName + " WHERE ts <> ?";
                    try (PreparedStatement deleteRowsStatement = connection.prepareStatement(deleteRowsSQL)) {
                        deleteRowsStatement.setString(1, ts1);
                        deleteRowsStatement.executeUpdate();
                    }

                    // Inserción de nuevos datos
                    String insertWeatherSQL = "INSERT INTO " + tableName + " (cloud, windSpeed, temp, humidity, instant, propRain, ts, ss, lat, lon) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertWeatherSQL)) {
                        insertStatement.setString(1, cloud);
                        insertStatement.setString(2, windSpeed);
                        insertStatement.setString(3, temp);
                        insertStatement.setString(4, humidity);
                        insertStatement.setString(5, instant);
                        insertStatement.setString(6, propRain);
                        insertStatement.setString(7, ts1);
                        insertStatement.setString(8, ss);
                        insertStatement.setString(9, lat);
                        insertStatement.setString(10, lon);

                        insertStatement.executeUpdate();
                    }

                    // Confirmar la transacción
                    connection.commit();
                    System.out.println("Weather database created");
                } catch (SQLException exc) {
                    // Si ocurre un error, hacer rollback de la transacción
                    connection.rollback();
                    exc.printStackTrace();
                } finally {
                    // Restaurar la configuración de autocommit
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException | NullPointerException exc) {
                exc.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


        @Override
        public void hotelStore(String message) {
            try {
                JsonObject jsonObject = new Gson().fromJson(message, JsonObject.class);
                JsonArray ratesArray = jsonObject.getAsJsonArray("rates");
                JsonObject dateObject = jsonObject.getAsJsonObject("date");
                String country = dateObject.getAsJsonPrimitive("country").getAsString();
                String name = dateObject.getAsJsonPrimitive("name").getAsString();
                String hotelKey = dateObject.getAsJsonPrimitive("hotelKey").getAsString();
                String checkIn = dateObject.getAsJsonPrimitive("checkIn").getAsString();
                String checkOut = dateObject.getAsJsonPrimitive("checkOut").getAsString();


                String ts = jsonObject.getAsJsonPrimitive("ts").getAsString();
                String ss = jsonObject.getAsJsonPrimitive("ss").getAsString();

                LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.parse(ts), ZoneId.systemDefault());
                String ts1 = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm"));

                try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\cgsos\\Downloads\\DataBase\\hotel.db")) {
                    String tableName = country.toLowerCase().replace(" ", "_");
                    try {
                        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                                "name TEXT," +
                                "hotelKey TEXT," +
                                "checkIn TEXT," +
                                "checkOut TEXT," +
                                "ss TEXT," +
                                "ts TEXT," +
                                "code TEXT," +
                                "rateName TEXT," +
                                "rate TEXT," +
                                "tax TEXT" +
                                ")";

                        try (Statement statement = connection.createStatement()) {
                            statement.executeUpdate(createTableSQL);
                            System.out.println("Created table for: " + tableName);
                        }
                    } catch (SQLException exc) {
                        exc.printStackTrace();
                    }


                    connection.setAutoCommit(false);

                    try {
                        // Eliminar filas con ts diferente al nuevo ts
                        String deleteRowsSQL = "DELETE FROM " + tableName + " WHERE ts <> ?";
                        try (PreparedStatement deleteRowsStatement = connection.prepareStatement(deleteRowsSQL)) {
                            deleteRowsStatement.setString(1, ts1);
                            deleteRowsStatement.executeUpdate();
                        }

                        // Insertar nuevos datos
                        for (JsonElement rateElement : ratesArray) {
                            JsonObject rateObject = rateElement.getAsJsonObject();
                            String code = rateObject.getAsJsonPrimitive("code").getAsString();
                            String rateName = rateObject.getAsJsonPrimitive("rateName").getAsString();
                            String rate = rateObject.getAsJsonPrimitive("rate").getAsString();
                            String tax = rateObject.getAsJsonPrimitive("tax").getAsString();

                            String insertRateSQL = "INSERT INTO " + tableName + " (name, hotelKey, checkIn, checkOut, ss, ts, code, rateName, rate, tax) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                            try (PreparedStatement insertStatement = connection.prepareStatement(insertRateSQL)) {
                                insertStatement.setString(1, name);
                                insertStatement.setString(2, hotelKey);
                                insertStatement.setString(3, checkIn);
                                insertStatement.setString(4, checkOut);
                                insertStatement.setString(5, ss);
                                insertStatement.setString(6, ts1);
                                insertStatement.setString(7, code);
                                insertStatement.setString(8, rateName);
                                insertStatement.setString(9, rate);
                                insertStatement.setString(10, tax);

                                insertStatement.executeUpdate();
                            }
                        }

                        connection.commit();
                        System.out.println("Hotel database created");
                    } catch (SQLException exc) {
                        connection.rollback();
                        exc.printStackTrace();
                    } finally {
                        connection.setAutoCommit(true);
                    }
                } catch (SQLException | NullPointerException exc) {
                    exc.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}


