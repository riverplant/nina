package org.rabbitmq.api.exchange.confirmlistener;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 
 * @author riverplant
 * 生产端
 */
public class SendConfirmListener {
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
		String exchangeName = "test_confirmlistener_exchange";
		//选择topic的方式，routingKey能使用正则
		String routingKey1 = "confirm.save";
			
		String msg = "Hello World RabbitMQ confirm listener Message ...";
		//5.发送,开启确认模式
		channel.confirmSelect();
		//添加事务，但是会很严重影响性能，不推荐使用
		//channel.txSelect();
		
		/**
		 * 为channel加上异步confirm监听,等待broker应答
		 */
		channel.addConfirmListener(new ConfirmListener() {
            /**
             * 如果出现网络闪断，可能会收不到
             */
			@Override
			public void handleAck(long deliveryTag, boolean multiple) throws IOException {
				System.out.println("------ok----");
			}

			@Override
			public void handleNack(long deliveryTag, boolean multiple) throws IOException {
				System.out.println("------error----");
			}
			
		});
		channel.basicPublish(exchangeName, routingKey1, null, msg.getBytes());
//		channel.close();
//		connection.close();
	}
}
