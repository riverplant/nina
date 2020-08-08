package org.rabbitmq.api.exchange.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 
 * @author riverplant
 *
 */
public class SenderTopicExchange {
	public static void main(String[] args) throws Exception {
		// 1.创建ConncetionFacotry
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("127.0.0.1");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		// 自动重连
		connectionFactory.setAutomaticRecoveryEnabled(true);
		// 每3秒确认一次
		connectionFactory.setNetworkRecoveryInterval(3000);

		// 2.创建Connection
		Connection connection = connectionFactory.newConnection();
		// 3.创建Channel
		Channel channel = connection.createChannel();
		// 4.申明
		String exchangeName = "test_topic_exchange";
		//选择topic的方式，routingKey能使用正则
		String routingKey1 = "user.save";
		String routingKey2 = "user.update";
		String routingKey3 = "user.delete.abc";
		
		String msg = "Hello World RabbitMQ Topic Exchange Message ...";
		//5.发送
		channel.basicPublish(exchangeName, routingKey1, null, msg.getBytes());
		channel.basicPublish(exchangeName, routingKey2, null, msg.getBytes());
		channel.basicPublish(exchangeName, routingKey3, null, msg.getBytes());

		channel.close();
		connection.close();
	}
}
