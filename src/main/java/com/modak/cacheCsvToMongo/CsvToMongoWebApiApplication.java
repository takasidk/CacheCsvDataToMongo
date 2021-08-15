package com.modak.cacheCsvToMongo;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@Slf4j
@SpringBootApplication
@EnableEncryptableProperties
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class})
public class CsvToMongoWebApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsvToMongoWebApiApplication.class, args);
		log.info("Succesfully started the application");
	}
}