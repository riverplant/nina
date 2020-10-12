package com.riverplant.rabbit.common.convert;

import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import com.google.common.base.Preconditions;
import com.riverplant.rabbit.common.serializer.Serializer;
/**
 * 
 * @author riverplant
 *  将自己定义的message和 org.springframework.amqp.core.Message
 *  进行序列化和反序列化的转化
 */
public class GenericMessageConverter implements MessageConverter{

	private Serializer serializer;
	public GenericMessageConverter(Serializer serializer) {
		Preconditions.checkNotNull(serializer);
		this.serializer = serializer;
	}
	@Override
	public org.springframework.amqp.core.Message toMessage(Object object, MessageProperties messageProperties)
			throws MessageConversionException {
		return new org.springframework.amqp.core.Message(this.serializer.serializeRaw(object) , messageProperties);
	}
	@Override
	public Object fromMessage(org.springframework.amqp.core.Message message) throws MessageConversionException {
		return this.serializer.deserialize(message.getBody());
	}

}
