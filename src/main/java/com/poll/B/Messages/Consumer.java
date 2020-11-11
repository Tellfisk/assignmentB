package com.poll.B.Messages;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String [] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();

        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel();
            channel.queDeclare("queue-name", false, false, false, null);

            String message = "message";

            channel.basicPublish("", "queue-name", false, null, message.getBytes());
            System.out.println("Message sent");
        }
    }

}
