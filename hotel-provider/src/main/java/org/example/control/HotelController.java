package org.example.control;

import org.example.model.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HotelController {
    private HotelProvider hotelProvider;
    private HotelSend hotelSend;

    public HotelController(HotelProvider hotelProvider, HotelSend hotelSend) {
        this.hotelProvider = hotelProvider;
        this.hotelSend = hotelSend;
    }

    public void execute(){
        LocalDate localDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        if (currentTime.isAfter(LocalTime.of(17, 0))) {
            localDate = localDate.plusDays(1);
        }

        String checkIn = localDate.format(DateTimeFormatter.ISO_DATE);
        String checkOut = localDate.plusDays(5).format(DateTimeFormatter.ISO_DATE);


        Date madrid1 = new Date(checkIn,checkOut, "g187514-d234293","Principe Pio Hotel","Madrid" );
        Date madrid2 = new Date(checkIn,checkOut, "g187514-d9998605","Only You Hotel","Madrid" );
        Date madrid3 = new Date(checkIn,checkOut, "g187514-d13313533","Barceló Imagine","Madrid" );
        Date barcelona1= new Date(checkIn,checkOut, "g187497-d24119358","Ramblas Hotel","Barcelona" );
        Date barcelona2= new Date(checkIn,checkOut, "g1080420-d10441271","Hotel Eden Park","Barcelona" );
        Date barcelona3= new Date(checkIn,checkOut, "g652121-d598483","Hotel HL Club Playa Blanca","Barcelona" );
        Date sevilla1= new Date(checkIn,checkOut, "g187443-d236135","Melia Sevilla","Sevilla" );
        Date sevilla2= new Date(checkIn,checkOut, "g187443-d191204","Hotel Colón Gran Meliá","Sevilla" );
        Date sevilla3= new Date(checkIn,checkOut, "g187443-d565125","Hotel Adriano Sevilla","Sevilla" );
        Date valencia1= new Date(checkIn,checkOut, "g187529-d206949","Hotel Malcom and Barret","Valencia" );
        Date valencia2= new Date(checkIn,checkOut, "g187529-d559406","Hotel Las Arenas Balneario Resort","Valencia" );
        Date valencia3= new Date(checkIn,checkOut, "g187529-d1774986","SH Valencia Palace","Valencia" );
        Date vigo1= new Date(checkIn,checkOut, "g187509-d206996","NH Collection Vigo","Vigo" );
        Date vigo2 = new Date(checkIn,checkOut, "g187507-d289800","Hotel Plaza Vigo","Vigo" );
        Date vigo3= new Date(checkIn,checkOut, "g187509-d11980017","B&B HOTEL Vigo","Vigo" );
        Date cadiz1= new Date(checkIn,checkOut, "g187432-d622669","Senator Cadiz Spa Hotel","Cádiz" );
        Date cadiz2= new Date(checkIn,checkOut, "g187432-d231578","Monte Puertatierra Hotel","Cádiz" );
        Date cadiz3= new Date(checkIn,checkOut, "g187432-d25179749","Hotel Cádiz Bahía","Cádiz" );
        Date pamplona1= new Date(checkIn,checkOut, "g187520-d232830","NH Pamplona Iruna Park","Pamplona" );
        Date pamplona2= new Date(checkIn,checkOut, "g187520-d228484","Hotel Tres Reyes","Pamplona" );
        Date pamplona3= new Date(checkIn,checkOut, "g187520-d236222","Gran Hotel La Perla","Pamplona" );
        Date malaga1= new Date(checkIn,checkOut, "g187438-d650588","Barceló Málaga","Málaga" );
        Date malaga2= new Date(checkIn,checkOut, "g187438-d498946","Atarazanas Malaga Boutique Hotel","Málaga" );
        Date malaga3= new Date(checkIn,checkOut, "g187438-d23843116","H10 Croma Málaga","Málaga" );



        List<Date> dates = List.of(
                madrid1, madrid2, madrid3,
                barcelona1, barcelona2, barcelona3,
                sevilla1, sevilla2, sevilla3,
                valencia1, valencia2, valencia3,
                vigo1, vigo2, vigo3,
                cadiz1, cadiz2, cadiz3,
                pamplona1, pamplona2, pamplona3,
                malaga1, malaga2, malaga3
        );
        ArrayList<Hotel> hotels= new ArrayList<>();

        callWeatherGet(dates, hotels);
        callStored(dates);


    }

    public ArrayList<Hotel> callWeatherGet( List<Date> islandHotel, ArrayList<Hotel> hotels) {
        for (Date iteredLocation : islandHotel) {
                Hotel hotel = hotelProvider.getHotel(iteredLocation);
                hotels.add(hotel);

        }
        System.out.println(hotels.size());
        return hotels;
    }
    public  void callStored( List<Date> dates) {
        for (Date date : dates) {
            hotelSend.sendBroker(hotelProvider.getHotel(date));

        }
    }


}
