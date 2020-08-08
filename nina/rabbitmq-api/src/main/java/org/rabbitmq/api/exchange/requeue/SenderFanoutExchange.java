package org.rabbitmq.api.exchange.requeue;

import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 
 * @author riverplant
 *
 */
public class SenderFanoutExchange {
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
		String queName = "test001";
		channel.queueDeclare(queName, true, false, false, null);
		// 5.发送
		for (int i = 0; i < 10; i++) {
			String msg = "Hello World RabbitMQ fanout Exchange Message ...";
			Map<String, Object> headers = new HashMap<>();
			headers.put("flag", i);
			BasicProperties props = new BasicProperties.Builder().deliveryMode(2).contentEncoding("UTF-8")
					.headers(headers).build();

			channel.basicPublish("", queName, props, msg.getBytes());
		}
		channel.close();
		connection.close();
	}
}
