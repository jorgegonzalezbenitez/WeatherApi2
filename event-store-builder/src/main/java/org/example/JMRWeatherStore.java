package org.example;

import javax.jms.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.example.model.Weather;

import java.time.Instant;

public class JMRWeatherStore implements WeatherRecieve{

    private static String url;
    private static String topicName;
    private static String clientId;

    public JMRWeatherStore(String url,String topicName,String clientId) {
        this.url=url;
        this.topicName=topicName;
        this.clientId=clientId;
    }

    @Override
    public void receiveBroker() {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.setClientID(clientId);
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic(topicName);

            TopicSubscriber durableSubscriber = session.createDurableSubscriber((Topic) destination, "DurableSubscriber");

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

                        // Ahora puedes hacer lo que quieras con el objeto Weather
                        System.out.println("Received Weather: " + json);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });

            // Mantén el hilo principal esperando para recibir mensajes
            Thread.sleep(Long.MAX_VALUE);

            connection.close();
        } catch (JMSException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

