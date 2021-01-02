package group.zealot.king.core.zt.aop;


import group.zealot.king.base.ServiceCode;
import group.zealot.king.base.exception.BaseRuntimeException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;

@Aspect
@Component
public class ZTValidAop {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Before("execution(* group.zealot..*(..))")
    public void before(JoinPoint joinPoint) {
        aopZTValid(joinPoint);
    }

    private void aopZTValid(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        boolean checkAll = method.isAnnotationPresent(ZTValid.class);
        Parameter[] parameters = method.getParameters();
        Object[] args = joinPoint.getArgs();
        int length = args.length;
        for (int i = 0; i < length; i++) {
            Parameter parameter = parameters[i];
            Object arg = args[i];
            if (checkAll || parameter.isAnnotationPresent(ZTValid.class)) {
                ZTValid ztValid = parameter.getAnnotation(ZTValid.class);
                if (ztValid == null) {
                    ztValid = method.getAnnotation(ZTValid.class);
                }
                if (ztValid.NotNull()) {
                    notNull(arg, ztValid.msg());
                }
                if (ztValid.NotEmpty()) {
                    notEmpty(arg, ztValid.msg());
                }
                if (ztValid.NotBlank()) {
                    notBlank(arg, ztValid.msg());
                }
                if (ztValid.length() > 0) {
                    length(arg, ztValid.length(), ztValid.msg());
                }
            }
        }
    }

    private void notNull(Object obj, String msg) {
        if (obj == null) {
            throwException(msg);
        }
    }

    private void notEmpty(Object obj, String msg) {
        notNull(obj, msg);
        if (getLength(obj) == 0) {
            throwException(msg);
        }
    }

    private void notBlank(Object obj, String msg) {
        notNull(obj, msg);
        if (obj instanceof String) {
            if (((String) obj).trim().length() == 0) {
                throwException(msg);
            }
        } else {
            throw new BaseRuntimeException("参数类型异常 无长度属性 不允许使用此注解");
        }
    }

    private void length(Object obj, int length, String msg) {
        notNull(obj, msg);
        if (getLength(obj) > length) {
            throwException(msg);
        }
    }

    private int getLength(Object obj) {
        if (obj instanceof byte[]) {
            return ((byte[]) obj).length;
        }
        if (obj instanceof short[]) {
            return ((short[]) obj).length;
        }
        if (obj instanceof float[]) {
            return ((float[]) obj).length;
        }
        if (obj instanceof double[]) {
            return ((double[]) obj).length;
        }
        if (obj instanceof boolean[]) {
            return ((boolean[]) obj).length;
        }
        if (obj instanceof char[]) {
            return ((char[]) obj).length;
        }
        if (obj instanceof int[]) {
            return ((int[]) obj).length;
        }
        if (obj instanceof long[]) {
            return ((long[]) obj).length;
        }
        if (obj instanceof Object[]) {
            return ((Object[]) obj).length;
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).size();
        }
        throw new BaseRuntimeException("参数类型异常 无长度属性 不允许使用此注解");
    }

    private void throwException(String msg) {
        if (msg == null || msg.trim().length() == 0) {
            throw new BaseRuntimeException(ServiceCode.PARAM_VALID_FAILD);
        } else {
            throw new BaseRuntimeException(ServiceCode.PARAM_VALID_FAILD, msg);
        }
    }
}
