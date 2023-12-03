package org.example;

import javax.jms.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.example.model.Weather;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.jms.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

public class JMRWeatherStore implements WeatherReceive{

    private static String url;
    private static String topicName;
    private static String clientId;

    public JMRWeatherStore(String url,String topicName,String clientId) {
        this.url=url;
        this.topicName=topicName;
        this.clientId=clientId;
    }

    @Override
    public List<String>  receiveBroker() {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.setClientID(clientId);
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic(topicName);

            TopicSubscriber durableSubscriber = session.createDurableSubscriber((Topic) destination, "DurableSubscriber");

            List<String> receivedWeatherList = new ArrayList<>();

            durableSubscriber.setMessageListener(message -> {
                if (message instanceof ObjectMessage) {
                    ObjectMessage objectMessage = (ObjectMessage) message;
                    try {
                        String json = (String) objectMessage.getObject();
                        // Aquí puedes realizar operaciones adicionales si es necesario
                        receivedWeatherList.add(json);

                        System.out.println("Received Weather: " + json);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });

            // Esperar hasta que se reciba un mensaje
            while (receivedWeatherList.isEmpty()) {
                Thread.sleep(100);
            }

            connection.close();
            return receivedWeatherList;
        } catch (JMSException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
