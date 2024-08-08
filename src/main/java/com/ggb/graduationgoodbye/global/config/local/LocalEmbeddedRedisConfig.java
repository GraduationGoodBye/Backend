package com.ggb.graduationgoodbye.global.config.local;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.embedded.RedisServer;

import java.io.IOException;

@Configuration
@Profile("local")
public class LocalEmbeddedRedisConfig {

  private RedisServer redisServer;

  @Value("${spring.data.redis.host}")
  private String redisHost;
  @Value("${spring.data.redis.port}")
  private int redisPort;

  private static final String REDIS_SERVER_MAX_MEMORY = "maxmemory 512M";

  @PostConstruct
  public void startRedis() throws IOException {
    redisServer = RedisServer.builder()
        .port(redisPort)
        .setting(REDIS_SERVER_MAX_MEMORY)
        .build();
    redisServer.start();
  }

  @PreDestroy
  public void stopRedis() {
    if (redisServer != null) {
      redisServer.stop();
    }
  }

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration();
    redisConfiguration.setHostName(redisHost);
    redisConfiguration.setPort(redisPort);
    return new LettuceConnectionFactory(redisConfiguration);
  }

  @Bean
  public RedisTemplate<?, ?> redisTemplate() {
    RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory());
    return redisTemplate;
  }
}
