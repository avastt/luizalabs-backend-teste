package br.com.luizalabs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest(classes = WhishlistApplicationTests.class)
class WhishlistApplicationTests {

	@Autowired
	ApplicationContext applicationContext;
	
	@Test
	void contextLoads() {
		Assertions.assertNotNull(applicationContext);
	}

}
