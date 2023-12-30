package org.example.control;

import java.util.Timer;
import java.util.TimerTask;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class HotelMain {
    private static String topicName = "supplier.Hotel";


    public static void main(String[] args) {
        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                HotelProvider hotelProvider = new HotelMapProvider();
                HotelSend hotelSend = new JMSHotelStore(args[0], topicName);
                HotelController hotelController = new HotelController(hotelProvider,hotelSend);
                hotelController.execute();
            }
        };

        long time_of_execution = 21600 * 1000;
        timer.schedule(timerTask, 0, time_of_execution);
    }
}
