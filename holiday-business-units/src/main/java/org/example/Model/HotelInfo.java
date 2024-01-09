package org.example.Model;

public class HotelInfo {
    private final int precioTotal;
    private final String hotelName;
    private final String rateName;
    private final int rate;

    public HotelInfo(int precioTotal, String hotelName, String rateName,int rate) {
        this.precioTotal = precioTotal;
        this.hotelName = hotelName;
        this.rateName = rateName;
        this.rate=rate;
    }

    public int getRate() {
        return rate;
    }

    public int getPrecioTotal() {
        return precioTotal;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getRateName() {
        return rateName;
    }
}
