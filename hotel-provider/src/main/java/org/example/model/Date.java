package org.example.model;



public class Date {

    private final String name;
    private final String hotelKey;

    private final String checkIn;
    private final String checkOut;
    private final String country;


    public Date(String checkIn, String checkOut, String hotelKey, String name,String country){
        this.hotelKey = hotelKey;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.name = name;
        this.country = country;
    }

    public String getHotelKey() {
        return hotelKey;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return country;
    }
}
