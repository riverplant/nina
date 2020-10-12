package org.rabbitmq.api.helloworld;

import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 
 * @author riverplant
 *  生产者
 */
public class Sender {

	public static void main( String[] args ) throws Exception
    {
    	//1.创建ConncetionFacotry
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("127.0.0.1");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		
		//2.创建Connection
		Connection connection = connectionFactory.newConnection();
		//3.创建Channel
		Channel channel = connection.createChannel();
		//4.申明
		String queueName = "test001";
		/**
		 * 参数:queue名字 
		 * 是否持久化:服务重启后是否存在
		 * 独占的queue(仅供此连接)
		 * 不使用时是否自动删除
		 * 其他参数
		 */
		channel.queueDeclare(queueName, false, false, false, null);
		//自定义属性
		Map<String,Object>headers = new HashMap<>();
		/**
		 * 定义消息
		 */
		AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
				 .deliveryMode(2)//持久化消息
				 .contentEncoding("UTF-8")
				 .headers(headers).build();
		for(int i=0; i<5; i++) {
			String msg = "Hello World RabbitMQ " + i;
			/**
			 * 
			 */
			channel.basicPublish("", queueName, props, msg.getBytes());
			System.out.println(" [x] Sent '" + msg + i +"'");
		}
    }
}
