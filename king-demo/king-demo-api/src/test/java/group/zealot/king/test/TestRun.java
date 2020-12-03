package group.zealot.king.test;

import group.zealot.king.core.zt.spring.SpringUtil;
import group.zealot.king.demo.api.Run;
import org.springframework.boot.SpringApplication;

public class TestRun {
    /**
     * java -jar api.jar --server.port=8080
     */
    public static void main(String[] args) {
        SpringUtil.setApplicationContext(SpringApplication.run(Run.class, args));
    }
}
