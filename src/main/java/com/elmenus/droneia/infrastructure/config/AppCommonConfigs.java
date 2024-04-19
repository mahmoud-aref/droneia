package com.elmenus.droneia.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.util.concurrent.Executor;

@Configuration
@EnableScheduling
@EnableWebFlux
@EnableAsync
public class AppCommonConfigs {

    @Bean(name = "mainAsyncExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Droneia-Async-");
        executor.initialize();
        return executor;
    }

}
