package org.example.control;
import org.example.model.*;
import java.sql.*;
import java.time.Instant;

class WeatherStoreSqlite implements WeatherStore {
    @Override
    public void save(Location location, Instant instant) {
        WeatherProvider weatherProvider = new WeatherMapProvider(WeatherMapProvider.getAPI_KEY());
        Weather weather = weatherProvider.getWeather(location, instant);

        if (weather != null) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/cgsos/OneDrive/Documentos/db_weather/WeatherDB.db");
                String tableName =  location.getName().toLowerCase().replace(" ", "_");
                String CreateSQL = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                        "temperature REAL," +
                        "humidity INTEGER," +
                        "clouds INTEGER," +
                        "wind REAL," +
                        "pop REAL," +
                        "instant TEXT" +
                        ")";

                System.out.println("Created table for " + location.getName());
                Statement statement = connection.createStatement();
                statement.executeUpdate(CreateSQL);
                if (WeatherDataExist(connection, tableName, instant.toString())) {
                    String UpdateSQL = "UPDATE " + tableName + " SET temperature=?, humidity=?, clouds=?, wind=?, pop=? " +
                            "WHERE instant=?";
                    PreparedStatement statementUpdate = connection.prepareStatement(UpdateSQL);
                    statementUpdate.setDouble(1, weather.getTemp());
                    statementUpdate.setInt(2, weather.getHumidity());
                    statementUpdate.setInt(3, weather.getClouds());
                    statementUpdate.setDouble(4, weather.getSpeed());
                    statementUpdate.setDouble(5, weather.getPop());
                    statementUpdate.setString(6, instant.toString());
                    statementUpdate.executeUpdate();
                } else {
                    String insertSQL = "INSERT INTO " + tableName + " (temperature, humidity, clouds, wind, pop, instant)" +
                            " VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement statementInsert = connection.prepareStatement(insertSQL);

                    statementInsert.setDouble(1, weather.getTemp());
                    statementInsert.setInt(2, weather.getHumidity());
                    statementInsert.setInt(3, weather.getClouds());
                    statementInsert.setDouble(4, weather.getSpeed());
                    statementInsert.setDouble(5, weather.getPop());
                    statementInsert.setString(6, instant.toString());
                    statementInsert.executeUpdate();
                }

                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No weather data found for " + location.getName() + " at " + instant);
        }
    }

    private boolean WeatherDataExist(Connection connection, String tableName, String instant) throws SQLException {
        String checkSQL = "SELECT COUNT(*) FROM " + tableName + " WHERE instant = ?";
        try (PreparedStatement checkStatement = connection.prepareStatement(checkSQL)) {
            checkStatement.setString(1, instant);
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                } else {
                    throw new SQLException("Error del registro meteorológico.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error del registro meteorológico.", e);
        }
    }
    }