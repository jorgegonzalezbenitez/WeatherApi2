package org.example.control;
import org.example.model.*;
import java.sql.*;
import java.time.Instant;
public class WeatherStoreSqlite implements WeatherStore {
    @Override
    public void save(Weather weather) {
    }
    @Override
    public void load(Location location, Instant instant) {
        WeatherProvider weatherProvider = new WeatherMapProvider();
        Weather weather = weatherProvider.getWeather(location, instant);

        if (weather != null) {
            Connection connection = null;
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/cgsos/OneDrive/Documentos/db_weather/db_weatherr.db");

                String tableName = location.getName().toLowerCase().replace(" ", "_"); // Sanitize for table name
                String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                        "name TEXT ," +
                        "clouds INTEGER," +
                        "wind REAL," +
                        "temperature REAL," +
                        "humidity INTEGER," +
                        "instant TEXT," +
                        "pop REAL" +
                        ")";
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(createTableSQL);
                }

                // Check if the record already exists before inserting
                if (!doesWeatherRecordExist(connection, tableName, instant.toString())) {
                    String insertWeatherSQL = "INSERT INTO " + tableName + " (name, clouds, wind, temperature, humidity, instant, pop) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(insertWeatherSQL)) {
                        preparedStatement.setString(1, location.getName());
                        preparedStatement.setInt(2, weather.getClouds()); // Assuming getClouds returns an integer
                        preparedStatement.setDouble(3, weather.getSpeed());
                        preparedStatement.setDouble(4, weather.getTemp());
                        preparedStatement.setInt(5, weather.getHumidity());
                        preparedStatement.setString(6, weather.getInstant().toString());
                        preparedStatement.setDouble(7, weather.getPop());
                        preparedStatement.executeUpdate();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("No weather data found for " + location.getName() + " at " + instant);
        }
    }

    private static boolean doesWeatherRecordExist(Connection connection, String tableName, String instant) throws SQLException {
        String checkSQL = "SELECT COUNT(*) FROM " + tableName + " WHERE instant = ?";
        try (PreparedStatement checkStatement = connection.prepareStatement(checkSQL)) {
            checkStatement.setString(1, instant);
            ResultSet resultSet = checkStatement.executeQuery();
            return resultSet.getInt(1) > 0;
        }
    }
}
