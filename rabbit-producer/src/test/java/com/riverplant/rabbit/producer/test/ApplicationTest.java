package com.riverplant.rabbit.producer.test;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;

import com.riverplant.rabbit.producer.component.RabbitSender;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

	@Autowired 
	private RabbitSender rabbitSender;
	
	@Test
	public void testSender() {
		
		Map<String,Object> properties = new HashMap<>();
		properties.put("attr1", "123456");
		properties.put("attr2", "abcd");
		rabbitSender.send("hello,rabbitmq", properties);
	}
	
	HttpHeaders createHeaders(String username, String password){
		   return new HttpHeaders() {{
		         String auth = username + ":" + password;
		         byte[] encodedAuth = Base64.encodeBase64( 
		            auth.getBytes(Charset.forName("US-ASCII")) );
		         String authHeader = "Basic " + new String( encodedAuth );
		         set( "Authorization", authHeader );
		      }};
		}
}
