package org.example.Model;

public interface CommandSet {

    void WeatherDataQuery(String country, String checkInDate, String checkOutDate);
    void HotelDataQuery(String country, String checkIn, String checkOut);
}
