package org.example;

import javax.jms.*;

import com.google.gson.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.example.model.Weather;

import java.io.BufferedWriter;
import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
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

public class JMRWeatherStore implements WeatherReceive {

    private static String url;
    private static String topicName;
    private static String clientId;

    public JMRWeatherStore(String url, String topicName, String clientId) {
        this.url = url;
        this.topicName = topicName;
        this.clientId = clientId;
    }

    @Override
    public List<Weather> receiveBroker() {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.setClientID(clientId);
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic(topicName);

            TopicSubscriber durableSubscriber = session.createDurableSubscriber((Topic) destination, "DurableSubscriber");

            List<Weather> receivedWeatherList = Collections.synchronizedList(new ArrayList<>());

            durableSubscriber.setMessageListener(message -> {
                if (message instanceof ObjectMessage) {
                    ObjectMessage objectMessage = (ObjectMessage) message;
                    try {
                        String json = (String) objectMessage.getObject();
                        Gson gson = new GsonBuilder()
                                .registerTypeAdapter(Instant.class, (JsonDeserializer<Instant>) (jsonElement, type, jsonDeserializationContext) ->
                                        Instant.ofEpochSecond(jsonElement.getAsLong()))
                                .create();

                        Weather weather = gson.fromJson(json, Weather.class);

                        // Asegúrate de manejar la sincronización correctamente
                        synchronized (receivedWeatherList) {
                            receivedWeatherList.add(weather);
                        }

                        System.out.println("Received Weather: " + weather);
                        System.out.println(receivedWeatherList.size());
                        WriteDirectory.writeWeatherListToDirectory(receivedWeatherList);

                    } catch (JMSException e) {
                        // Puedes agregar aquí lógica adicional si es necesario
                    }
                }
            });

            // Esperar hasta que se alcance el tiempo de espera
            Thread.sleep(Long.MAX_VALUE); // Espera máximo 30 segundos (ajusta según tus necesidades)

            connection.close();
            return receivedWeatherList;
        } catch (JMSException | InterruptedException e) {
            // Manejar la excepción de manera significativa para tu aplicación
            throw new RuntimeException("Error while receiving JMS message", e);
        }
    }
}
