package com.shoplive.videostoragedemo.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfig {

  private static final int CORE_POOL_SIZE = 3;
  private static final int MAX_POOL_SIZE = 10;
  private static final int QUEUE_CAPACITY = 100_000;

  @Bean(name = "resizeTaskExecutor")
  public ThreadPoolTaskExecutor resizeTaskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(CORE_POOL_SIZE);
    executor.setMaxPoolSize(MAX_POOL_SIZE);
    executor.setQueueCapacity(QUEUE_CAPACITY);
    return executor;
  }

}
