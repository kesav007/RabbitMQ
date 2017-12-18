package com.kesav.RabbitMQ;

import java.io.UnsupportedEncodingException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class ConsumerEx1 {
	public static void main(String[] args) throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setUsername("guest");
		factory.setPassword("guest");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume("KesavTestQueue", consumer);
		
		boolean removeAllUpTo = true;
		while(true) {
			Delivery delivery = consumer.nextDelivery();
			if(delivery == null) break;
			if(processMessage(delivery)){
				long deliveryTag = delivery.getEnvelope().getDeliveryTag();
				channel.basicAck(deliveryTag, removeAllUpTo);
			}
		}		
	}

	private static boolean processMessage(Delivery delivery) throws UnsupportedEncodingException {
		String msg = new String(delivery.getBody(), "UTF-8");
		System.out.println("[x] Recv: redeliver = " +delivery.getEnvelope().isRedeliver() + ". msg = " + msg);
		return true;
	}
}
