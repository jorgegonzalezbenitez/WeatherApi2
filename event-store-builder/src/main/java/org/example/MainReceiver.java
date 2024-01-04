package org.example;

import javax.jms.JMSException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainReceiver {


    public static void main(String[] args) throws JMSException {
        Subscriber subscriber = new AMQTopicSubscriber(args[0]);
        FileStateEventBuilder listener = new FileStateEventBuilder(args[1]);
        subscriber.start(listener);
    }
}