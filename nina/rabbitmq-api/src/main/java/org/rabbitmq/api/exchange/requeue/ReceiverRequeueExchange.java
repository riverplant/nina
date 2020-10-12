package org.rabbitmq.api.exchange.requeue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * 
 * @author riverplant
 *
 */
public class ReceiverRequeueExchange {
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
		String queueName = "test001";
		channel.queueDeclare(queueName, true, false, false, null);
		
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            try {
				Thread.sleep(1000);
				if((Integer)delivery.getProperties().getHeaders().get("flag") == 0) {
					System.out.println("异常,进入Nack");
					//throw new RuntimeException("异常");
					/**
					 * long deliveryTag, 
					 * boolean multiple, 
					 * boolean requeue:是否重回队列，一般设置为false,如果为true,会可能出现无限循环
					 */
					channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);
				} else {
					channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
        };
        /**
         * 限流
         * prefetchSize：消息体大小，0为不限制 
         * prefetchCount:同一时间只能消费一条消息 
         * global：
         */
        channel.basicQos(0, 1, false);
        /**
         * boolean autoAck = false 设置为手动线程
         */
        channel.basicConsume(queueName, false, deliverCallback, consumerTag -> { });
	}
}
