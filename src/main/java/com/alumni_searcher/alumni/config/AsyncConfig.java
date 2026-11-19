
package com.alumni_searcher.alumni.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "phantomTaskExecutor")
    public Executor phantomTaskExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        // Minimum number of background threads
        executor.setCorePoolSize(2);

        // Maximum number of background threads
        executor.setMaxPoolSize(5);

        // Maximum number of tasks waiting in the queue
        executor.setQueueCapacity(20);

        // Name for background threads
        executor.setThreadNamePrefix("phantom-");

        executor.initialize();

        return executor;
    }
}
