package org.example;

import javax.jms.JMSException;

public class DataLakeMain {


    public static void main(String[] args) throws JMSException {
        Subscriber subscriber = new DurableSubscriber(args[0]);
        DataLake listener = new DataLake(args[1]);
        subscriber.start(listener);
    }
}