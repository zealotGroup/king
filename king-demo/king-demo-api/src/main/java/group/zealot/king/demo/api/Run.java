package group.zealot.king.demo.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(scanBasePackages = "group.zealot.king")
@ServletComponentScan
public class Run {
    public static void main(String[] args) {
        SpringApplication.run(Run.class, args);
    }
}
