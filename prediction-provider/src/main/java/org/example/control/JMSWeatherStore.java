package org.example.control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.example.model.Weather;

import javax.jms.*;
import java.time.Instant;


public class JMSWeatherStore implements WeatherSend{
        private static String url;
        private static String topicName;

    public JMSWeatherStore(String url, String topicName) {
        this.url = url;
        this.topicName =topicName;
    }

        @Override
        public void sendBroker(Weather weather) {
            try{

                ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
                Connection connection = connectionFactory.createConnection();
                connection.start();

                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                Destination destination = session.createTopic(topicName);

                MessageProducer producer = session.createProducer(destination);

                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(Instant.class, (JsonSerializer<Instant>) (src, typeOfSrc, context) ->
                                context.serialize(src.getEpochSecond()))
                        .create();

                String json = gson.toJson(weather);


                ObjectMessage objectMessage = session.createObjectMessage(json);

                System.out.println(json);
                producer.send(objectMessage);
                connection.close();
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        }

}
