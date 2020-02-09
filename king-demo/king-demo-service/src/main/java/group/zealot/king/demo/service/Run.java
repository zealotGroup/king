package group.zealot.king.demo.service;

import group.zealot.king.core.zt.spring.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(scanBasePackages = "group.zealot.king")
@EnableScheduling
@EnableAsync
public class Run {
    private static Logger logger = LoggerFactory.getLogger(Run.class);

    public static void main(String[] args) {
        logger.info("启动");
        SpringUtil.setApplicationContext(SpringApplication.run(Run.class, args));
        logger.info("启动结束");
    }
}
