package group.zealot.king.core.zt.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig {
    @Bean
    public AsyncConfigurer asyncConfigurer(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        AsyncConfigurer asyncConfigurer = new AsyncConfigurer() {
            public Executor getAsyncExecutor() {
                return threadPoolTaskExecutor;
            }
        };
        return asyncConfigurer;
    }
}
