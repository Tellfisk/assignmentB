package com.poll.B.Messages;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();

        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel();
            channel.queueDeclare("queue-name", false, false, false, null);

            String message = "message that you want";

            channel.basicPublish("", "queue-name", false, null, message.getBytes());
            System.out.println("Message sent");
        }
    }
}
