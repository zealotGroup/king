package group.zealot.king.demo.web;

import group.zealot.king.core.shiro.realm.ShiroService;
import group.zealot.king.core.zt.dubbo.DubboUtil;
import group.zealot.king.core.zt.spring.SpringUtil;
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
public class Run extends SpringBootServletInitializer implements ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(Run.class);

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
//        initDubboService(SpringUtil.getApplicationContext());
    }

    /**
     * 分布式 使用dubbo获取服务时调用
     */
    private void initDubboService(ApplicationContext applicationContext) {
        logger.info("启动 initDubboService");
        applicationContext.getBean(DubboUtil.class).registReference();
        //重新导入ShiroService 依赖【有依赖是dubbo service】//也许不用了
//        applicationContext.getBean(SpringUtil.class).autowireBean(applicationContext.getBean(ShiroService.class));
    }
}
