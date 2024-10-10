package com.withalion.uteg_test;

import com.withalion.uteg_test.controller.JsonParserController;
import com.withalion.uteg_test.view.ApplicationInterface;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
class UtegTestApplicationTests {
	@Autowired
	private ApplicationContext context;
	@Autowired
	private JsonParserController jsonParserController;


	@Test
	void contextLoads() {
	}

	/**
	 * This tests if CommandLineRunner is not loaded in test environment, so we can test other functionality
	 */
	@Test
	void whenContextLoads_thenRunnersAreNotLoaded() {
		assertThrows(NoSuchBeanDefinitionException.class,
				() -> context.getBean(ApplicationInterface.class),
				"CommandLineRunner should not be loaded during this test");
	}

	@Test
	void jsonParserLoads() {
		assertThat(jsonParserController).isNotNull();
	}
}
