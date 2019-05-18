package group.zealot.king.demo.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "group.zealot.king")
public class Run {

    public static void main(String[] args) {
        SpringApplication.run(Run.class, args);
    }
}
