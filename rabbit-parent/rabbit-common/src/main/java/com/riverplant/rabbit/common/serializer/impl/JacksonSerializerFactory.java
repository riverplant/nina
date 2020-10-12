package com.riverplant.rabbit.common.serializer.impl;

import com.riverplant.rabbit.api.model.Message;
import com.riverplant.rabbit.common.serializer.Serializer;
import com.riverplant.rabbit.common.serializer.SerializerFactory;

public class JacksonSerializerFactory implements SerializerFactory{

	public static final SerializerFactory instance = new JacksonSerializerFactory();
	
	@Override
	public Serializer create() {		
		return JacksonSerializer.createParametricType(Message.class);
	}

}
