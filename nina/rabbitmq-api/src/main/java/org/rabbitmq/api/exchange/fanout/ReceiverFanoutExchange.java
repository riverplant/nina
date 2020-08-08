package org.rabbitmq.api.exchange.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * 
 * @author riverplant
 *
 */
public class ReceiverFanoutExchange {
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
		String queueName = "test_fanout_queue";
		String exchangeName = "test_fanout_exchange";
		//选择fanout的方式，不需要设置routingKey
		String exchangType = "fanout";
		String routingKey = "";
		channel.exchangeDeclare(exchangeName, exchangType, true, false, false, null);
		channel.queueDeclare(queueName, false, false, false, null);
		channel.queueBind(queueName, exchangeName, routingKey);
		/**
		 * void handle(String consumerTag, Delivery message) throws IOException;
	     * Called when a <code><b>basic.deliver</b></code> is received for this consumer.
	     * @param consumerTag the <i>consumer tag</i> associated with the consumer
	     * @param message the delivered message
	     * @throws IOException if the consumer encounters an I/O error while processing the message
	     */
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });

	}
}
