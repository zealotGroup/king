package group.zealot.king.demo.service;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "group.zealot.king")
@EnableScheduling
@EnableAsync
@EnableDubbo(scanBasePackages = "group.zealot.king")
public class Run {
    private static Logger logger = LoggerFactory.getLogger(Run.class);
    public static ConfigurableApplicationContext applicationContext;

    public static void main(String[] args) {
        logger.info("启动");
        applicationContext = SpringApplication.run(Run.class, args);
        logger.info("启动结束");
    }
}
