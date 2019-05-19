package group.zealot.king.core.zt.dubbo;

import group.zealot.king.base.exception.BaseRuntimeException;
import group.zealot.king.core.zt.mif.Services;
import group.zealot.king.core.zt.spring.ClassScan;
import group.zealot.king.core.zt.spring.SpringUtil;
import org.apache.dubbo.config.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Set;

@Component
public class DubboUtil {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ClassScan classScan;
    @Autowired(required = false)
    private ApplicationConfig applicationConfig;
    @Autowired(required = false)
    private RegistryConfig registryConfig;
    @Autowired(required = false)
    private ProtocolConfig protocolConfig;
    @Autowired(required = false)
    private ProviderConfig providerConfig;
    @Autowired(required = false)
    private ConsumerConfig consumerConfig;

    @Autowired
    private SpringUtil springUtil;
    @Autowired
    private Services services;

    /**
     * 注册 服务 到dubbo上
     */
    public void registService() {
        String basePackages = "classpath*:group/zealot/king/core/db/mybatis/**/serviceImpl/**/*.class";
        registService(basePackages);
    }

    /**
     * 注册 服务 到dubbo上
     */
    public void registService(String basePackages) {
        Class<? extends Annotation> annotationType = Service.class;
        Set<Object> beanSet;
        try {
            beanSet = classScan.scanGetBeanSet(annotationType, basePackages);
        } catch (IOException e) {
            logger.error("扫描class类报错annotationType:" + annotationType.getName() + " basePackages:" + basePackages);
            throw new BaseRuntimeException(e);
        }
        for (Object bean : beanSet) {
            exportService(bean);
        }
    }

    public void exportService(Object bean) {
        ServiceConfig service = new ServiceConfig();
        service.setApplication(applicationConfig);
        service.setRegistry(registryConfig);
        service.setProvider(providerConfig);
        service.setProtocol(protocolConfig);
        service.setInterface(bean.getClass().getInterfaces()[0]);
        service.setRef(bean);
        service.export();
    }

    /**
     * 注册 Reference 到spring容器，和 group.zealot.king.core.zt.mif.Services
     */
    public void registReference() {
        Set<String> set = getServiceBeanNameSet();
        for (String beanName : set) {
            Object bean;
            try {
                bean = getService(Class.forName(beanName));
            } catch (ClassNotFoundException e) {
                logger.error("registReference 异常");
                throw new BaseRuntimeException(e);
            }
            springUtil.regist(beanName, bean);//注入到spring容器
        }
        springUtil.autowireBean(services);
    }

    /**
     * 获取 服务接口 列表
     */
    public Set<String> getServiceBeanNameSet() {
        String basePackages = "classpath*:group/zealot/king/core/zt/mif/service/**/*.class";
        return getServiceBeanNameSet(basePackages);
    }

    /**
     * 获取 服务接口 列表
     */
    public Set<String> getServiceBeanNameSet(String basePackages) {
        Class<? extends Annotation> annotationType = MyDubboService.class;
        Set<String> set;
        try {
            set = classScan.scanGetBeanNameSet(annotationType, basePackages);
        } catch (IOException e) {
            logger.error("扫描class类报错annotationType:" + annotationType.getName() + " basePackages:" + basePackages);
            throw new BaseRuntimeException(e);
        }
        return set;
    }

    public <T> T getService(Class<T> interfaceClass) {
        ReferenceConfig<T> reference = new ReferenceConfig<>();
        reference.setConsumer(consumerConfig);
        reference.setApplication(applicationConfig);
        reference.setRegistry(registryConfig);
        reference.setInterface(interfaceClass);
        return reference.get();
    }
}
