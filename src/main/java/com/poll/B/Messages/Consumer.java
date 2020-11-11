package com.poll.B.Messages;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class Consumer {

    public Consumer() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare("queue-name",false,false,false,null);

            channel.basicConsume("queue-name",true,(consumerTag, message)-> {
                String m = new String(message.getBody(), StandardCharsets.UTF_8);
                System.out.println("Received message: " + m);
                httpPOST(m);
            }, consumerTag -> { });

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    private void httpPOST(String json) throws IOException {
        String postUrl = "http://dweet.io/dweet/for/omelette250";
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(postUrl);
        StringEntity postingString = new StringEntity(json);
        post.setEntity(postingString);
        post.setHeader("Content-type", "application/json");
        HttpResponse  response = httpClient.execute(post);
    }


}
