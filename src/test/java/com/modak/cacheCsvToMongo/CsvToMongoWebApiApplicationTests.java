package com.modak.cacheCsvToMongo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CsvToMongoWebApiApplicationTests {

	@Autowired
	private ApplicationContext appContext;
	@Test
	void contextLoads() {
	}

	@Test
	public void ConfiguredEncryptorbeanTest() {

		Environment environment = appContext.getBean(Environment.class);
		assertEquals(
				"d",
				environment.getProperty("spring.datasource.password"));
		assertEquals(
				"sa",
				environment.getProperty("spring.datasource.username"));
	}
}
