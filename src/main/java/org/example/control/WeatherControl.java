package org.example.control;

import org.example.model.*;

import java.util.ArrayList;

public class WeatherControl {
    public static void main(String[] args) {
        Location gran_canaria = new Location("Gran Canaria", 28.09973, -15.41343);
        Location tenerife = new Location("Tenerife", 28.46824, -16.25462);
        Location hierro = new Location("El hierro", 27.80628, -17.91578);
        Location gomera = new Location("La Gomera", 28.09163, -17.11331);
        Location fuerteventura = new Location("Fuerteventura", 28.50038, -13.86272);
        Location palma = new Location("La Palma", 28.68351, -17.76421);
        Location lanzarote = new Location("Lanzarote", 28.96302, -13.54769);
        Location graciosa = new Location("La Graciosa", 29.23147, -13.50341);

        ArrayList<Location> islands = new ArrayList<>();
        islands.add(lanzarote);
        islands.add(fuerteventura);
        islands.add(gran_canaria);
        islands.add(tenerife);
        islands.add(hierro);
        islands.add(gomera);
        islands.add(palma);
        islands.add(graciosa);


        for (int i = 0; i < 8; i++) {
            WeatherProvider.getWeather(islands.get(i));

        }
    }
}
