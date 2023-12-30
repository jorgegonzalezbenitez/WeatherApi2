package org.example;

import javax.jms.JMSException;
import java.io.File;

public class MainReceiver {
    private static final String topicNameWeather = "supplier.Hotel";
    private static final String directory = "eventstore" + File.separator + "prediction.Weather";


    public static void main(String[] args) throws JMSException {
        Subscriber subscriber = new AMQTopicSubscriber(args[0]);
        FileStateEventBuilder listener = new FileStateEventBuilder(directory);
        subscriber.start(listener,topicNameWeather);
    }
}