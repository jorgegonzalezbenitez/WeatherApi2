package org.example.control;
import org.example.model.*;
import java.time.Instant;


public interface WeatherStore {
    void save(Weather weather);
    void load(Location location, Instant instant);
}
