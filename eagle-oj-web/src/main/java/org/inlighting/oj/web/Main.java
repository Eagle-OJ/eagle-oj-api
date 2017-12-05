package org.inlighting.oj.web;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author Smith
 **/
public class Main {
    public static void main(String[] args) throws Exception {
        /*ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("106.14.145.89");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("test_queue", false, false, false, null);
        String message = "Hello World!";
        channel.basicPublish("", "test_queue", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");*/
        accept();
    }

    public static void accept() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("106.14.145.89");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare("test_queue", false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume("test_queue", true, consumer);
    }
}
