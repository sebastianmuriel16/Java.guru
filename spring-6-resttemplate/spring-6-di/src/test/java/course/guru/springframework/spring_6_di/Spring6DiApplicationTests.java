package course.guru.springframework.spring_6_di;

import course.guru.springframework.spring_6_di.controllers.MyControllers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class Spring6DiApplicationTests {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	MyControllers myController;
	@Test
	void testAutowiredOfController(){
		System.out.println(myController.sayHello());
	}

	@Test
	void testGetControllerFromCtx(){
		MyControllers myController = applicationContext.getBean(MyControllers.class);

		System.out.println(myController.sayHello());
	}

	@Test
	void contextLoads() {
	}

}
