package org.example.control;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.example.model.*;
import org.jsoup.Jsoup;
import java.time.Instant;

public class WeatherMapProvider implements WeatherProvider{
    private static String API_KEY;


    public  WeatherMapProvider(String API_KEY) {
        this.API_KEY = API_KEY;

    }

    public static String getAPI_KEY() {
        return API_KEY;
    }
    @Override
    public  Weather getWeather(Location location, Instant instant) {
        Weather obj_weather = null;
        try {

            String url = "https://api.openweathermap.org/data/2.5/forecast?lat=" + location.getLat() + "&lon=" + location.getLon() + "&appid=" + API_KEY;
            String jsonString = Jsoup.connect(url).ignoreContentType(true).execute().body();
            Gson gson = new Gson();
            JsonObject weathers = gson.fromJson(jsonString, JsonObject.class);
            JsonArray lists = weathers.getAsJsonObject().getAsJsonArray("list");


            for (JsonElement list : lists) {
                JsonObject weather = list.getAsJsonObject();
                JsonObject city = weathers.getAsJsonObject("city");
                JsonObject main = weather.get("main").getAsJsonObject();
                JsonObject clouds = weather.get("clouds").getAsJsonObject();
                JsonObject wind = weather.get("wind").getAsJsonObject();

                String name = city.getAsJsonPrimitive("name").getAsString();
                double lat = city.getAsJsonObject("coord").getAsJsonPrimitive("lat").getAsDouble();
                double lon = city.getAsJsonObject("coord").getAsJsonPrimitive("lon").getAsDouble();
                double temp = main.get("temp").getAsDouble();
                int humidity = main.get("humidity").getAsInt();
                int all = clouds.get("all").getAsInt();
                double speed = wind.get("speed").getAsDouble();
                Double pop = weather.get("pop").getAsDouble();
                int dt = weather.get("dt").getAsInt();
                long unixTimestamp = dt;
                Instant weatherInstant = Instant.ofEpochSecond(unixTimestamp);
                Location place = new Location(name,lat,lon);


                if (weatherInstant.equals(instant)) {
                    obj_weather = new Weather(place,temp, humidity, all, speed, pop, weatherInstant);
                    break;

                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el clima desde OpenWeatherMap", e);

        }
        return obj_weather;
    }
}
