package com.riverplant.rabbit.common.serializer;
/**
 * 序列化和反序列化接口
 * @author riverplant
 *
 */
public interface Serializer {
	//序列化
	byte[] serializeRaw(Object data);
	
	//序列化
	String serialize(Object data);
	
	//反序列化
	<T> T deserialize(String content);
	
	//反序列化
	<T> T deserialize(byte[] content);
}
