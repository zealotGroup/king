package group.zealot.king.core.zt.spring;

import group.zealot.king.base.exception.BaseRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringUtil {
    private static ApplicationContext applicationContext;
    @Autowired
    private AutowireCapableBeanFactory beanFactory;
    @Autowired
    private DefaultListableBeanFactory defaultListableBeanFactory;

    public void regist(String beanName, Object bean) {
        defaultListableBeanFactory.registerSingleton(beanName, bean);
    }

    public void autowireBean(Object bean){
        //自动注入依赖
        beanFactory.autowireBean(bean);
    }

    public synchronized static void setApplicationContext(ApplicationContext applicationContext) {
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        } else {
            throw new BaseRuntimeException("SpringUtil.applicationContext 不允许重复赋值");
        }

    }

    public static ApplicationContext getApplicationContext() {
        return SpringUtil.applicationContext;
    }
}
