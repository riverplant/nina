package com.riverplant.rabbit.common.convert;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import com.google.common.base.Preconditions;

/**
 * 修饰者模式
 * @author riverplant
 *
 */
public class RabbitMessageConverter implements MessageConverter{

	private GenericMessageConverter delegate;
	private final String defaultExpire = String.valueOf(24 * 60 * 60 * 1000);
	public RabbitMessageConverter(GenericMessageConverter genericMessageConverter) {
		Preconditions.checkNotNull(genericMessageConverter);
		this.delegate = genericMessageConverter;
	}

	@Override
	public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {		
		//添加自定义的装饰
		messageProperties.setExpiration(defaultExpire);
		return this.delegate.toMessage(object, messageProperties);
	}
    /**
     * 写出成自己定义的message对象
     */
	@Override
	public Object fromMessage(Message message) throws MessageConversionException {
		//强转为自己定义的message
		com.riverplant.rabbit.api.model.Message msg = (com.riverplant.rabbit.api.model.Message) this.delegate.fromMessage(message);
		return msg;
	}

}
