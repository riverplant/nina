package com.riverplant.rabbit.api.model;

import lombok.Data;

/**
 * 定义消息的类型
 * @author riverplant
 *
 */
@Data
public final class MessageType {
    /**
     * 迅速消息，不需要保障消息的可靠性，也不需要做confirm确认
     */
	public static final  String RAPID = "0";
	
	/**
     * 确认消息，不需要保障消息的可靠性，需要做confirm确认
     */
	public static final  String CONFIRM = "1";
	
	/**
     * 可靠消息，一定要保障消息的可100%靠性，不容许消息丢失。需要做confirm确认
     * ps:需要和数据库保持原子性
     */
	public static final  String RELIANT = "2";
}
