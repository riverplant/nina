package org.nina.api.controller;
import javax.servlet.http.Cookie;

import org.junit.Before;
/**
 * 
 * @author riverplant
 *
 */
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.nina.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ItemsControllerTest {

	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void whenQuerySuccess() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/items")
				.param("name", "饺子")
				.param("catagoryId", "1")
				.param("page", "1")
				.param("size", "10")
				.param("sort","itemName desc","createdTime,asc")
				.accept(MediaType.APPLICATION_JSON_UTF8))
		        .andExpect(MockMvcResultMatchers.status().isOk())
		        //JSONPATH
		        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3));
	}
	
	@Test
	public void whenGetInfoSuccess() throws Exception {
		String result = mockMvc.perform(
				MockMvcRequestBuilders.get("/items/1")
				.accept(MediaType.APPLICATION_JSON_UTF8))
		        .andExpect(MockMvcResultMatchers.status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.itemName").value("饺子"))
		        .andReturn().getResponse().getContentAsString();
		System.out.println(result);
	}
	
	@Test
	public void whenCreateSuccess() throws Exception {
		String content = "{\"id\":null,\"itemName\":\"水果\",\"createdTime\":\"2019-12-29\"}";
		String result = mockMvc.perform(
				MockMvcRequestBuilders.post("/items")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		        .andExpect(MockMvcResultMatchers.status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
		        .andReturn().getResponse().getContentAsString();
		System.out.println(result);
	}
	
	@Test
	public void whenUpdateSuccess() throws Exception {
		String content = "{\"id\":1L,\"itemName\":\"大西瓜\",\"createdTime\":\"2019-12-29\"}";
		String result = mockMvc.perform(
				MockMvcRequestBuilders.put("/items/1")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		        .andExpect(MockMvcResultMatchers.status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.itemName").value("大西瓜"))
		        .andReturn().getResponse().getContentAsString();
		System.out.println(result);
	}
	
	@Test
	public void whenDeleteSuccess() throws Exception {
		        mockMvc.perform(
				MockMvcRequestBuilders.delete("/items/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		        .andExpect(MockMvcResultMatchers.status().isOk());	        
	}
	
	@Test
	public void whenCookieOrHeaderExists() throws Exception {
		        mockMvc.perform(
				MockMvcRequestBuilders.get("/items/1")
				.cookie(new Cookie("token","123456"))
				.header("auth", "xxxxxxx")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		        .andExpect(MockMvcResultMatchers.status().isOk());	        
	}
}
