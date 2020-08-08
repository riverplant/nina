package org.rabbitmq.api.exchange.dlx;

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
public class SenderDlxExchange {
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
		String exchangeName = "test_dlx_exchange";
		String routingKey = "group.bfxy";
		// 5.发送
			String msg = "Hello World RabbitMQ Dlx Exchange Message ...";
			Map<String, Object> headers = new HashMap<>();
			BasicProperties props = new BasicProperties
					.Builder()
					.deliveryMode(2)
					.contentEncoding("UTF-8")
					//TTL
					.expiration("6000")
					.headers(headers).build();

			channel.basicPublish(exchangeName, routingKey, props, msg.getBytes());
		
		channel.close();
		connection.close();
	}
}
