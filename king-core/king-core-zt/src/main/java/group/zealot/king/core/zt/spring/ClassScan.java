package group.zealot.king.core.zt.spring;


import group.zealot.king.base.exception.BaseRuntimeException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class ClassScan {
    protected final Log logger = LogFactory.getLog(getClass());
    @Autowired
    private ResourceLoader resourceLoader;

    private final ResourcePatternResolver resourcePatternResolver;
    private final MetadataReaderFactory metadataReaderFactory;

    public ClassScan() {
        super();
        resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);
    }

    /**
     * 扫描 特定包[group.zealot.king] 指定注解[annotationType]
     *
     * @return className
     */
    public Set<String> scanGetBeanNameSet(Class<? extends Annotation> annotationType) {
        return scanGetBeanNameSet(annotationType, "classpath*:group/zealot/king/**/*.class");
    }

    /**
     * 扫描 特定包[basePackages] 指定注解[annotationType]
     *
     * @return className
     */
    public Set<String> scanGetBeanNameSet(Class<? extends Annotation> annotationType, String basePackages) {
        logger.info("class scanGetBeanNameSet [" + annotationType.getName() + "],basePackages [" + basePackages + "]");
        AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(annotationType);
        Set<String> set = new HashSet<>();
        try {
            Resource[] resources = resourcePatternResolver.getResources(basePackages);
            for (Resource r : resources) {
                MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(r);
                if (annotationTypeFilter.match(metadataReader, metadataReaderFactory)) {
                    set.add(metadataReader.getClassMetadata().getClassName());
                }
            }
        } catch (IOException e) {
            logger.error("scanGetBeanNameSet 异常", e);
            throw new BaseRuntimeException(e);
        }
        if (set.isEmpty()) {
            logger.warn("No class was found in '" + basePackages + "' package. ");
        }
        return set;
    }

    /**
     * 扫描 特定包[basePackages]
     *
     * @return className
     */
    public Set<String> scanGetBeanNameSet(String basePackages) {
        logger.info("class scanGetBeanNameSet basePackages [" + basePackages + "]");
        Set<String> set = new HashSet<>();
        try {
            Resource[] resources = resourcePatternResolver.getResources(basePackages);
            for (Resource r : resources) {
                MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(r);
                set.add(metadataReader.getClassMetadata().getClassName());
            }
        } catch (IOException e) {
            logger.error("scanGetBeanNameSet 异常", e);
            throw new BaseRuntimeException(e);
        }
        if (set.isEmpty()) {
            logger.warn("No class was found in '" + basePackages + "' package. ");
        }
        return set;
    }

    /**
     * 扫描 特定包[group.zealot.king] 指定注解[annotationType]
     *
     * @return classBean
     */
    public Set<Object> scanGetBeanSet(Class<? extends Annotation> annotationType) {
        return getBeanSet(scanGetBeanNameSet(annotationType));
    }

    /**
     * 扫描 特定包[basePackages] 指定注解[annotationType]
     *
     * @return classBean
     */
    public Set<Object> scanGetBeanSet(Class<? extends Annotation> annotationType, String basePackages) {
        return getBeanSet(scanGetBeanNameSet(annotationType, basePackages));
    }

    /**
     * 扫描 特定包[group.zealot.king] 指定注解[annotationType]
     *
     * @return className:classBean
     */
    public Map<String, Object> scanGetBeanMap(Class<? extends Annotation> annotationType) {
        return getBeanMap(scanGetBeanNameSet(annotationType));
    }

    /**
     * 扫描 特定包[basePackages]
     *
     * @return className:classBean
     */
    public Map<String, Object> scanGetBeanMap(String basePackages) {
        return getBeanMap(scanGetBeanNameSet(basePackages));
    }

    /**
     * 扫描 特定包[basePackages] 指定注解[annotationType]
     *
     * @return className:classBean
     */
    public Map<String, Object> scanGetBeanMap(Class<? extends Annotation> annotationType, String basePackages) {
        return getBeanMap(scanGetBeanNameSet(annotationType, basePackages));
    }

    /**
     * 通过className,到spring容器[SpringUtil.getApplicationContext()]中获取classBean
     *
     * @return className:classBean
     */
    public Map<String, Object> getBeanMap(Set<String> set) {
        HashMap<String, Object> beanMap = new HashMap<>();
        if (SpringUtil.getApplicationContext() == null) {
            throw new BaseRuntimeException("null point of applicationContext");
        } else {
            for (String name : set) {
//                String beanName = name.substring(name.lastIndexOf(".") + 1);
                Object bean;
                try {
                    bean = SpringUtil.getApplicationContext().getBean(Class.forName(name));
                } catch (ClassNotFoundException e) {
                    throw new BaseRuntimeException(e);
                }
                beanMap.put(name, bean);
            }

        }
        if (beanMap.isEmpty()) {
            logger.warn("No Bean was found in scanGetBeanNameSet set.");
        }
        return beanMap;
    }

    /**
     * 通过className,到spring容器[SpringUtil.getApplicationContext()]中获取classBean
     *
     * @return classBean
     */
    public Set<Object> getBeanSet(Set<String> set) {
        HashSet<Object> beanSet = new HashSet<>();
        if (SpringUtil.getApplicationContext() == null) {
            throw new BaseRuntimeException("null point of applicationContext");
        } else {
            for (String name : set) {
//                String beanName = name.substring(name.lastIndexOf(".") + 1);
                Object bean;
                try {
                    bean = SpringUtil.getApplicationContext().getBean(Class.forName(name));
                } catch (ClassNotFoundException e) {
                    throw new BaseRuntimeException(e);
                }
                beanSet.add(bean);
            }

        }
        if (beanSet.isEmpty()) {
            logger.warn("No Bean was found in scanGetBeanNameSet set.");
        }
        return beanSet;
    }
}
