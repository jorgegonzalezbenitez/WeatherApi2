package org.example.control;
import org.example.model.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class WeatherControl {
    public WeatherProvider weatherProvider;

    public WeatherControl(WeatherProvider weatherProvider) {
        this.weatherProvider = weatherProvider;
    }

    public void execute() {
        Location gran_canaria = new Location("Gran Canaria", 28.09973, -15.41343);
        Location tenerife = new Location("Tenerife", 28.46824, -16.25462);
        Location hierro = new Location("Hierro", 27.80628, -17.91578);
        Location gomera = new Location("Gomera", 28.09163, -17.11331);
        Location fuerteventura = new Location("Fuerteventura", 28.50038, -13.86272);
        Location palma = new Location("Palma", 28.68351, -17.76421);
        Location lanzarote = new Location("Lanzarote", 28.96302, -13.54769);
        Location graciosa = new Location("Graciosa", 29.23147, -13.50341);

        List<Location> canary_islands = List.of(gran_canaria, tenerife,lanzarote,gomera,fuerteventura,palma,hierro,graciosa);

        
        ArrayList<Weather> climates =new ArrayList<>();
        ArrayList<Instant> times =new ArrayList<>();

        InstantCreated(times);
        WeatherCall(times,canary_islands,climates);
        SaveCall(times,canary_islands);

    }
    public static ArrayList<Instant> InstantCreated(ArrayList<Instant> times) {
        for (int i = 0; i < 5; i++) {
            LocalDate today = LocalDate.now();
            LocalTime hour = LocalTime.of(12, 0);
            LocalDateTime todayHour = LocalDateTime.of(today, hour);
            Instant todayInstant = todayHour.toInstant(ZoneOffset.UTC);
            Instant tomorrowInstant = todayInstant.plus(i, ChronoUnit.DAYS);
            times.add(tomorrowInstant);
        }
        return times;
    }
    public static ArrayList<Weather> WeatherCall(ArrayList<Instant> times, List<Location> canary_islands, ArrayList<Weather> climates) {
        WeatherProvider weatherProvider = new WeatherMapProvider(WeatherMapProvider.getAPI_KEY());
        for (Location iteredLocation : canary_islands) {
            for (Instant iteredInstant : times) {
                Weather weather = weatherProvider.getWeather(iteredLocation, iteredInstant);
                climates.add(weather);
            }
        }
        return climates;
    }

    public static void SaveCall(ArrayList<Instant> times, List<Location> canary_islands) {
        for (Location iteredLocation : canary_islands) {
            WeatherStore weatherStore = new WeatherStoreSqlite();
            for (Instant iteredInstant : times) {
                weatherStore.save(iteredLocation, iteredInstant);
            }
        }
    }
    public static void main(String[] args) {
        WeatherControl weatherControl = new WeatherControl( new WeatherMapProvider(WeatherMapProvider.getAPI_KEY()));
        weatherControl.execute();
    }
}