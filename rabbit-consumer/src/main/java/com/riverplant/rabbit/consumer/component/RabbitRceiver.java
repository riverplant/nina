package com.riverplant.rabbit.consumer.component;

import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
public class RabbitRceiver {
	/**
	 * 监听的方法
	 * @throws IOException 
	 */
	@RabbitListener(bindings = @QueueBinding(
			    value = @Queue(value = "${spring.rabbitmq.listener.order.queue.name}", 
			                   durable = "${spring.rabbitmq.listener.order.queue.durable}" ),
			    exchange = @Exchange(name = "${spring.rabbitmq.listener.order.exchange.name}", 
			                         durable = "${spring.rabbitmq.listener.order.exchange.durable}", 
			                         type="${spring.rabbitmq.listener.order.exchange.type}",
			                         ignoreDeclarationExceptions = "${spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions}"),
			    key = "${spring.rabbitmq.listener.order.exchange.key}"
			   ) //bindings
	        )
	@RabbitHandler
	public void onMessage(Message<?> msg, Channel channel) throws IOException {
		//1.收到消息后进行业务端消费处理		
		System.out.println("--------------");
		System.out.println("消费消息:" + msg.getPayload());
		//处理成功后获取deliveryTag，并且进行手工签收
		Long deliveryTag =	(Long) msg.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
		//手工签收
		channel.basicAck(deliveryTag, false);
	}
}
