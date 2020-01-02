package org.nina.service.test;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.nina.service.ServiceApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ServiceApplication.class)
@Transactional
public class BaseTest {

}
