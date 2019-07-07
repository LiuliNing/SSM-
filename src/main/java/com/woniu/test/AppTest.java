package com.woniu.test;



import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.woniu.entity.User;
import com.woniu.service.IUserService;

public class AppTest {
@Test
public void testName() throws Exception {
	ApplicationContext ax = //
			new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
	IUserService service = (IUserService) ax.getBean("userServiceImpl");
//	System.out.println(service.getClass());
	User u = new User();
	u.setName("ergoulalala");
	u.setPassword("123456");
	service.save(u);
}
}
