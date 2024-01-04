package org.example.control;
import org.example.model.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
public class WeatherController {
    private WeatherProvider weatherProvider;
    private WeatherSend weatherSend;

    public WeatherController(WeatherProvider weatherProvider, WeatherSend weatherSend) {
        this.weatherProvider = weatherProvider;
        this.weatherSend = weatherSend;
    }

    public void execute() {
        Location madrid = new Location("Madrid", 40.4168, -3.7038);
        Location barcelona = new Location("Barcelona", 41.3888, 2.159);
        Location sevilla = new Location("Sevilla", 37.3886, -5.9822);
        Location valencia = new Location("Valencia", 39.4699, -0.3763);
        Location vigo = new Location("Vigo", 42.2328, 8.7226);
        Location cadiz = new Location("Cádiz", 36.5298, -6.2925);
        Location pamplona = new Location("Pamplona", 42.8169, -1.6431);
        Location malaga = new Location("Málaga", 36.7213, -4.4214);

        List<Location> canary_islands = List.of(madrid,barcelona,sevilla,valencia,vigo,cadiz,pamplona,malaga);

        
        ArrayList<Weather> climates =new ArrayList<>();
        ArrayList<Instant> times =new ArrayList<>();

        instantCreate(times);
        weatherCall(times,canary_islands,climates);
        saveCall(times,  canary_islands);


    }
    public  ArrayList<Instant> instantCreate(ArrayList<Instant> times) {
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
    public  ArrayList<Weather> weatherCall(ArrayList<Instant> times, List<Location> canary_islands, ArrayList<Weather> climates) {
        for (Location iteredLocation : canary_islands) {
            for (Instant iteredInstant : times) {
                Weather weather = weatherProvider.getWeather(iteredLocation, iteredInstant);
                climates.add(weather);
            }
        }
        System.out.println(climates.size());
        return climates;
    }
    public  void saveCall(ArrayList<Instant> times, List<Location> canary_islands) {
        for (Location iteredLocation : canary_islands) {
            for (Instant iteredInstant : times) {
                weatherSend.sendBroker(weatherProvider.getWeather(iteredLocation,iteredInstant));
            }
        }
    }
}