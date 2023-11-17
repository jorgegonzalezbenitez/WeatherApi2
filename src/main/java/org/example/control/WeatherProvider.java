package org.example.control;

import com.google.gson.Gson;
import org.example.model.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jsoup.Jsoup;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;

public interface WeatherProvider {
    Weather getWeather(Location location,Instant instant);
}
