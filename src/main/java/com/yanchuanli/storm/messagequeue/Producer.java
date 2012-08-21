package com.yanchuanli.storm.messagequeue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.yanchuanli.storm.others.ServerConfig;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Author: Yanchuan Li
 * Date: 5/27/12
 * Email: mail@yanchuanli.com
 */
public class Producer {
    private static Logger log = Logger.getLogger(Producer.class);
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ServerConfig.rabbitMQServerAddress);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String message = "hello all";
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());

        channel.close();
        connection.close();
    }
}
