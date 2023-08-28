package com.backend.topcariving.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import redis.embedded.RedisServer;

@Configuration
class TestRedisConfig {

	private final RedisServer redisServer;

	public TestRedisConfig(@Value("${spring.redis.port}") int redisPort) {
		this.redisServer = RedisServer.builder()
			.port(redisPort)
			.setting("maxmemory 10M")
			.build();
	}

	@PostConstruct
	public void startRedids() {
		this.redisServer.start();
	}

	@PreDestroy
	public void stopRedis() {
		this.redisServer.stop();
	}
}
