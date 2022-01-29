package com.example.async.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync // 비동기 사용 가능하도록 추가
@Configuration
public class AsyncConfig extends AsyncConfigurerSupport {
    @Override
    public AsyncTaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(15);
        executor.setQueueCapacity(20); // queue 개수를 넘으면 에러발생
        executor.setThreadNamePrefix("TEST-ASYNC-");
        executor.initialize();

        return executor;
    }
}
