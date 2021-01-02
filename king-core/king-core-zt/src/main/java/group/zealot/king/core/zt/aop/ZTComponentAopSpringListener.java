package group.zealot.king.core.zt.aop;

import group.zealot.king.base.exception.BaseRuntimeException;
import group.zealot.king.core.zt.spring.SpringListener;
import group.zealot.king.core.zt.spring.SpringUtil;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;

@Component
public class ZTComponentAopSpringListener extends SpringListener {

    @Override
    protected int getOrder() {
        return 2;
    }

    @Override
    protected void exec() {
        Map<String, Object> map = classScan.scanGetBeanMap(ZTComponent.class);
        map.forEach(this::ztAutowired);
    }

    private void ztAutowired(String beanName, Object bean) {
        Class<?> clazz = bean.getClass();
        ztAutowired(clazz);
    }

    public void ztAutowired(Class<?> clazz) {
        try {
            field:
            for (Field field : clazz.getDeclaredFields()) {
                try {
                    Class<?> fieldClazz = field.getType();
                    Map<String, ?> map = SpringUtil.getApplicationContext().getBeansOfType(fieldClazz);
                    for (Map.Entry<String, ?> item : map.entrySet()) {
                        if (!item.getKey().startsWith("group.zealot.king")) {
                            field.set(null, item.getValue());
                            logger.info("注入[{}]属性[{}]成功（本地bean）:", clazz.getName(), field.getName());
                            continue field;
                        }
                    }
                    for (Map.Entry<String, ?> item : map.entrySet()) {
                        if (item.getKey().startsWith("group.zealot.king")) {
                            field.set(null, item.getValue());
                            logger.info("注入[{}]属性[{}]成功（RPCbean）:", clazz.getName(), field.getName());
                            continue field;
                        }
                    }
                    throw new BaseRuntimeException("未找到@ZTComponent注解的Bean[" + fieldClazz.getName() + "]");
                } catch (Exception e) {
                    logger.debug("注入[" + clazz.getName() + "]属性[" + field.getName() + "]失败:", e);
                    logger.warn("注入[" + clazz.getName() + "]属性[" + field.getName() + "]失败:" + e.getMessage());
                }
            }
        } catch (Exception e) {
            logger.error("ztAutowired [" + clazz.getName() + "]失败", e);
            throw new BaseRuntimeException("ztAutowired [" + clazz.getName() + "]失败");
        }
    }

}
