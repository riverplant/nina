package org.nina.service.test;

import org.junit.Test;
import org.nina.domain.Sex;
import org.nina.dto.UserCondition;
import org.nina.repository.UserRepository;
import org.nina.repository.spec.UserSpec;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceTest extends BaseTest{
	 //@Autowired UserRepository userRepository;
	 
	 @Test
	 public void test1() {
		 UserCondition condition = new UserCondition();
		 condition.setUsername("test");
		 condition.setAge(16);
		 condition.setAgeTo(42);
		 condition.setSex(Sex.MAN);
		 //userRepository.findAll(new UserSpec(condition));
		 //Assert.assertEquals(info.getItemName(), itemsRepository.getOne(info.getId()).getItemName());
	 }
}
