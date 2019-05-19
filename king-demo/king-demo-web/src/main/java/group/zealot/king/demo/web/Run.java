package group.zealot.king.demo.web;

import group.zealot.king.core.shiro.realm.ShiroService;
import group.zealot.king.core.zt.dubbo.DubboUtil;
import group.zealot.king.core.zt.mif.service.system.SysUserService;
import group.zealot.king.core.zt.spring.SpringUtil;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@SpringBootApplication(scanBasePackages = "group.zealot.king")
@EnableDubbo
public class Run extends SpringBootServletInitializer implements ApplicationContextAware {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Run.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Run.class, args);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.setApplicationContext(applicationContext);
        initDubboService(SpringUtil.getApplicationContext());
    }

    public void initDubboService(ApplicationContext applicationContext) {
        applicationContext.getBean(DubboUtil.class).registReference();

        applicationContext.getBean(ShiroService.class)
                .setSysUserService(applicationContext.getBean(SysUserService.class));
    }
}
