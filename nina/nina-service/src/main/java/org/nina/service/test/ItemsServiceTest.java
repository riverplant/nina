package org.nina.service.test;

import org.junit.Assert;
import org.junit.Test;
import org.nina.dto.ItemsInfo;
import org.nina.repository.ItemsRepository;
import org.nina.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;

public class ItemsServiceTest extends BaseTest {
	@Autowired ItemsRepository itemsRepository;
	 //@Autowired ItemsService itemsService;
	 
	 @Test
	 public void whenCreateSuccess() {
		 long count = itemsRepository.count();
		 ItemsInfo info = new ItemsInfo();
		 info.setItemName("测试食品");
		 //info = itemsService.create(info);
		// Assert.assertEquals(count + 1, itemsRepository.count());
		 //Assert.assertNotNull(info.getId());
		// Assert.assertEquals(info.getItemName(), itemsRepository.getOne(info.getId()).getItemName());
	 }
	 
	 @Test
	 public void whenUpdateSuccess() {
		 long count = itemsRepository.count();
		 ItemsInfo info = new ItemsInfo();
		 info.setItemName("测试食品2");
		 //info = itemsService.update(info);
		// Assert.assertEquals(count, itemsRepository.count());
		// Assert.assertEquals(info.getItemName(), itemsRepository.getOne(info.getId()).getItemName());
	 }
}
