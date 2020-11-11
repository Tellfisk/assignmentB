package com.poll.B.Messages;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {

    public Consumer() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare("queue-name",false,false,false,null);

            channel.basicConsume("queue-name",true,(consumerTag,message)-> {
                String m = new String(message.getBody(), "UTF-8");
                System.out.println("Recieved message: " + m);
            }, consumerTag -> { });

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

}
