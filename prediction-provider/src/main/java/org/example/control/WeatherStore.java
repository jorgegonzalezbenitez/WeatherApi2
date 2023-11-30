package org.example.control;

import org.example.model.Location;

import java.time.Instant;

public interface WeatherStore {
    void save(Location location, Instant instant);
}
