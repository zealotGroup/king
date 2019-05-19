package group.zealot.king.demo.service;

import group.zealot.king.core.zt.dubbo.DubboUtil;
import group.zealot.king.core.zt.spring.SpringUtil;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(scanBasePackages = "group.zealot.king")
@EnableScheduling
@EnableAsync
@EnableDubbo
@EnableJms
public class Run {
    private static Logger logger = LoggerFactory.getLogger(Run.class);

    public static void main(String[] args) {
        logger.info("启动");
        SpringUtil.setApplicationContext(SpringApplication.run(Run.class, args));
        init();
        logger.info("启动结束");
    }

    public static void init() {
        SpringUtil.getApplicationContext().getBean(DubboUtil.class).registService();
    }
}
