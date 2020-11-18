package org.nina.distribute.lock.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

/**
 * 创建 Controller 类，该类也类似于正常 Web 项目中 Controller 写法一样，
 * 在方法上面添加 @MessageMapping 注解，
 * 当客户端发送消息请求的前缀匹配上 WebSocket 配置类中的 /app 前缀后，
 * 会进入到 Controller 类中进行匹配，如果匹配成功则执行注解所在的方法内容。
 * @author riverplant
 *
 */
@Controller
public class MessageController {
	 /** 消息发送工具对象 */
    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;
    
    /** 广播发送消息，将消息发送到指定的目标地址 */
    @MessageMapping("/test")
    public void sendTopicMessage(MessageBody messageBody) {
        // 将消息发送到 WebSocket 配置类中配置的代理中（/topic）进行消息转发
        simpMessageSendingOperations.convertAndSend(messageBody.getDestination(), messageBody);
    }
}
