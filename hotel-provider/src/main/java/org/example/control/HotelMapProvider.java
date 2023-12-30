package org.example.control;

import com.google.gson.*;
import org.example.model.*;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.time.Instant;

public class HotelMapProvider implements HotelProvider {
    @Override
    public  Hotel getHotel(Date date) {
        Hotel hotel = null;
        try {
            String apiUrl = "https://data.xotelo.com/api/rates?hotel_key=" + date.getHotelKey() + "&chk_in=" + date.getCheckIn() + "&chk_out=" + date.getCheckOut();
            String jsonString = Jsoup.connect(apiUrl).ignoreContentType(true).execute().body();
            JsonObject responseJson = new Gson().fromJson(jsonString, JsonObject.class);

            JsonElement resultElement = responseJson.get("result");

            if (resultElement.isJsonObject()) {
                JsonObject resultObject = resultElement.getAsJsonObject();
                String checkInDate = resultObject.getAsJsonPrimitive("chk_in").getAsString();
                String checkOutDate = resultObject.getAsJsonPrimitive("chk_out").getAsString();
                Date date1 = new Date(checkInDate, checkOutDate, date.getHotelKey(), date.getName(), date.getLocation());

                long dt = responseJson.get("timestamp").getAsLong();
                Instant instant = Instant.ofEpochMilli(dt);



                JsonArray rates = resultObject.getAsJsonArray("rates");



                for (JsonElement rate : rates) {
                    JsonObject rateObject = rate.getAsJsonObject();

                    String code = rateObject.getAsJsonPrimitive("code").getAsString();
                    String name = rateObject.getAsJsonPrimitive("name").getAsString();
                    int rateName = rateObject.getAsJsonPrimitive("rate").getAsInt();
                    int tax = rateObject.getAsJsonPrimitive("tax").getAsInt();

                    hotel = new Hotel(code, name, rateName, tax,instant, date1);


                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(Instant.class, (JsonSerializer<Instant>) (src, typeOfSrc, context) ->
                                    context.serialize(src.toString()))
                            .create();

                    String json = gson.toJson(hotel);
                    System.out.println(json);




                }








            } else {
                // Manejar el caso en el que "result" no es un JsonObject (por ejemplo, es un JsonNull)
                // Puede lanzar una excepción, asignar un valor predeterminado, etc.
            }} catch (IOException e) {
        throw new RuntimeException(e);

}return hotel;
}
}