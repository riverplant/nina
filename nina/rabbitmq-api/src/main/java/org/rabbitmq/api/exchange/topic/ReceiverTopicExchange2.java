package org.rabbitmq.api.exchange.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * 
 * @author riverplant
 *
 */
public class ReceiverTopicExchange2 {
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
		String queueName = "test_topic_queue";
		String exchangeName = "test_direct_exchange";
		//选择直连的方式，routingKey必须完全匹配，不能使用正则
		String exchangType = "topic";
		String routingKey = "user.*";
		/**
	     * Declare an exchange, via an interface that allows the complete set of
	     * arguments.
	     * @see com.rabbitmq.client.AMQP.Exchange.Declare
	     * @see com.rabbitmq.client.AMQP.Exchange.DeclareOk
	     * @param exchange the name of the exchange
	     * @param type the exchange type
	     * @param durable connectiontrue if we are declaring a durable exchange (the exchange will survive a server restart)
	     * @param autoDelete true if the server should delete the exchange when it is no longer in use
	     * @param internal true if the exchange is internal, i.e. can't be directly
	     * published to by a client.
	     * @param arguments other properties (construction arguments) for the exchange
	     * @return a declaration-confirm method to indicate the exchange was successfully declared
	     * @throws java.io.IOException if an error is encountered
	     */
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
