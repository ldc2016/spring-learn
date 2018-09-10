package com.ldc.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

@Slf4j
@SpringBootApplication
@ImportResource("classpath:/applicationContext.xml")
public class StartApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(StartApplication.class, args);

		String[] activeProfiles = context.getEnvironment().getActiveProfiles();
		for (String profile : activeProfiles) {
			log.info("Spring Boot 使用profile为:{}" +profile);
		}

		log.info("******* start Application ******");
	}
}


