package org.rabbitmq.api.exchange.dlx;

import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * 
 * @author riverplant
 *
 */
public class ReceiverDlxExchange {
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
		// 4.声明正常的exchange,queue,routingkey
		String exchangeName = "test_dlx_exchange";
		String queueName = "test_dlx_queue";
		String exchangeType = "topic";
		String routingKey = "group.*";		
			
		channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
		//定义arguments用于开启死信队列
		Map<String, Object> arguments = new HashMap<>();
		arguments.put("x-dead-letter-exchange", "dlx.exchange");
		//添加参数arguments,开启了死信队列
		channel.queueDeclare(queueName, false, false, false, arguments);
		channel.queueBind(queueName, exchangeName, routingKey);
		
		// 声明DLX死信,必须与参数中定义的名字一致dlx.exchange
		channel.exchangeDeclare("dlx.exchange", exchangeType, true, false, false, null);
		channel.queueDeclare("dlx.queue", false, false, false, null);
		channel.queueBind("dlx.queue", "dlx_exchange", "#");
				
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            try {
				Thread.sleep(1000);
				if((Integer)delivery.getProperties().getHeaders().get("flag") == 0) {
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
        channel.basicConsume(exchangeName, false, deliverCallback, consumerTag -> { });
	}
}
