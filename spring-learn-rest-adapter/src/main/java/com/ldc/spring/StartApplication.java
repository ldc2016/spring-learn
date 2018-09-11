package com.ldc.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@Slf4j
//@SpringBootApplication
//@ImportResource("classpath:/applicationContext.xml")
@Slf4j
@SpringBootApplication
@ComponentScan(value={"com.ldc.spring"})
@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass=true)
//@ImportResource("classpath:/thrift-server.xml")
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


