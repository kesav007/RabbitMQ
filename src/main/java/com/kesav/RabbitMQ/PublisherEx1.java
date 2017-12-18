package com.kesav.RabbitMQ;

import java.io.IOException;
import java.util.Date;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class PublisherEx1 {
	
	public static void main(String[] args) throws IOException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setUsername("guest");
		factory.setPassword("guest");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		String queueName = "KesavTestQueue";
		for(int i=0; i<10; i++) {
//			String message = "Hello World, This message # " + i;
			Date message = new Date();
			channel.basicPublish("", queueName, null, message.toString().getBytes());
			System.out.println(" [x] send '" + message + "'");
		}
		channel.close();
		connection.close();
		
	}

}
