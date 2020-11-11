package com.poll.B.Messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    ConnectionFactory factory;
    Channel channel;

    public Producer(Object object) {
        factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection()) {
            channel = connection.createChannel();
            channel.queueDeclare("queue-name", false, false, false, null);

            ObjectMapper mapper = new ObjectMapper();
            try {
                String json = mapper.writeValueAsString(object);
                channel.basicPublish("", "queue-name", false, null, json.getBytes());
                System.out.println("Message sent");
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}
