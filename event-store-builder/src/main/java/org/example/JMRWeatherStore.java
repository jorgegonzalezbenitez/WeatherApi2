package org.example;

import javax.jms.*;
import com.google.gson.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.example.model.Weather;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JMRWeatherStore implements WeatherReceive {

    private static String url;
    private static String topicName;
    private static String clientId;
    private static String path;

    public JMRWeatherStore(String url, String topicName, String clientId,String path) {
        this.url = url;
        this.topicName = topicName;
        this.clientId = clientId;
        this.path = path;
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

                        synchronized (receivedWeatherList) {
                            receivedWeatherList.add(weather);
                        }

                        System.out.println("Received Weather: " + weather);
                        System.out.println(receivedWeatherList.size());
                        WriteDirectory.writeDirectory(receivedWeatherList,path);

                    } catch (JMSException e) {
                    }
                }
            });
            Thread.sleep(Long.MAX_VALUE);

            connection.close();
            return receivedWeatherList;
        } catch (JMSException | InterruptedException e) {
            throw new RuntimeException("Error while receiving JMS message", e);
        }
    }
}
