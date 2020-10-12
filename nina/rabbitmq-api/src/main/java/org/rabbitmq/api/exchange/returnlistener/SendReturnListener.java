package org.rabbitmq.api.exchange.returnlistener;

import java.io.IOException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ReturnListener;

/**
 * 
 * @author riverplant
 * 生产端
 */
public class SendReturnListener {
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
		String exchangeName = "test_returnlistener_exchange";
		//选择topic的方式，routingKey能使用正则
		String routingKey1 = "confirm.save";
			
		String msg = "Hello World RabbitMQ return listener Message ...";
		//5.监听
		/**
		 * 为channel加上异步confirm监听,等待broker应答
		 */
		channel.addReturnListener(new ReturnListener() {
            /**
             * replyCode:302
             */
			@Override
			public void handleReturn(int replyCode, 
					                 String replyText, 
					                 String exchange, 
					                 String routingKey, 
					                 BasicProperties properties, 
					                 byte[] body)
					throws IOException {
				System.out.println("*****handleReturn*****");
				System.out.println("replyCode:"+replyCode);
				System.out.println("replyText:"+replyText);
				System.out.println("exchange:"+exchange);
				System.out.println("routingKey"+routingKey);
				System.out.println("body" + new String(body));
			}
            
			
		});
		//必须设置mandatory = true
		boolean mandatory = true;
		//6.发送
		channel.basicPublish(exchangeName, routingKey1, mandatory, null, msg.getBytes());
//		channel.close();
//		connection.close();
	}
}
