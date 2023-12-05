package org.example;

import javax.jms.JMSException;

public class MainReceiver {
    private static String topicName = "prediction.Weather";

    public static void main(String[] args) throws JMSException {
        Subscriber subscriber = new AMQTopicSubscriber(args[0]);
        FileStateEventBuilder listener = new FileStateEventBuilder(args[1]);
        subscriber.start(listener,topicName);
    }
}