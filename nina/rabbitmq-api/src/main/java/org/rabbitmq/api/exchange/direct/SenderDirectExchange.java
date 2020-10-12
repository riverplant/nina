package org.rabbitmq.api.exchange.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 
 * @author riverplant
 *
 */
public class SenderDirectExchange {
	public static void main(String[] args) throws Exception {
		// 1.创建ConncetionFacotry
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("127.0.0.1");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");

		// 2.创建Connection
		Connection connection = connectionFactory.newConnection();
		// 3.创建Channel
		Channel channel = connection.createChannel();
		// 4.申明交换机名称
		String exchangeName = "test_direct_exchange";
		
		String routingKey = "test_direct_routingKey";
		//5.发送
		String msg = "Hello World RabbitMQ Direct Exchange Message ...";
		//routingKey必须和队列名称保持一致
		channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());
		
	}
}
