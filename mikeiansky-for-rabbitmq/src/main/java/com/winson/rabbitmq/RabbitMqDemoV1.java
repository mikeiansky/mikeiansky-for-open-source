package com.winson.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author winson
 * @date 2022/6/18
 **/
public class RabbitMqDemoV1 {

    public static void main(String[] args) {

        String queueName = "winson-rabbit-mq";

        System.out.println("Hello rabbit mq!");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            System.out.println("connect channel : " + channel);

            channel.queueDeclare(queueName, false, false, false, null);
            String message = "Hello World!";
            channel.basicPublish("", queueName, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

}
