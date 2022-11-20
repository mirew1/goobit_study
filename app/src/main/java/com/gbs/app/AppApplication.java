package com.gbs.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession(redisNamespace="myredis") // Redis에 세션 보관 설정
@EnableRedisRepositories(basePackages = "com.gbs.app.nss")
@SpringBootApplication(scanBasePackages = "com.gbs.app.nss")
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

}
