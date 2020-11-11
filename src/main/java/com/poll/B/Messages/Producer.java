package com.poll.B.Messages;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    ConnectionFactory factory;
    Channel channel;
    String message;

    public Producer(String message) {
        factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection()) {
            channel = connection.createChannel();
            channel.queueDeclare("queue-name", false, false, false, null);

            this.message = message;
            
            channel.basicPublish("", "queue-name", false, null, message.getBytes());
            System.out.println("Message sent");
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
