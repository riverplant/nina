package org.rabbitmq.api.exchange.fanout;

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
		String exchangeName = "test_fanout_exchange";
		//选择fanout的方式，routingKey不需要设置		
		//5.发送
		for(int i=0; i< 10; i++ ) {
			String msg = "Hello World RabbitMQ fanout Exchange Message ...";	
			channel.basicPublish(exchangeName, "", null, msg.getBytes());
		}		
		channel.close();
		connection.close();
	}
}
