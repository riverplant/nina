package org.nina.api.config;

import org.nina.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定时任务，关闭过期未支付的订单
 * @author riverplant
 *
 */
@Component
public class OrderTask {
	@Autowired private OrderService orderService;
	/**
	 * 使用定时任务，会存在的弊端
	 * 1.会有时间差，无法做到准确的时间点
	 * 2.不支持集群,只能通过一台计算机，单独的运行
	 * 3.会对数据库全表搜索，及其影响性能
	 * 4.后续课程会通过MQ->RabbitMQ,RocketMQ,KafKa
	 *   延时任务(队列)
	 *   
	 */
    //自动关闭订单
	//cron.qqe2.com
	//@Scheduled(cron = "0 0 0/1 * * ?")
	public void autoCloseOrder() {
		orderService.closeOrder();
	}
}
