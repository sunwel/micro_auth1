package com.enlink.authclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 客户端业务请求测试的启动类
 */
@ComponentScan(basePackages = { "com.enlink.authclient" })
@SpringBootApplication
public class ClientBusinessApplicationTest implements CommandLineRunner {

	@Autowired
	private ClientBusinessTest clientBusinessTest;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ClientBusinessApplicationTest.class);
		app.setWebEnvironment(false);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
//		clientBusinessTest.testGetRandomInt();
//		clientBusinessTest.testGetRandomLong();
//		clientBusinessTest.testGetSecretString();
		clientBusinessTest.getRandomPriv3();
	}
}
