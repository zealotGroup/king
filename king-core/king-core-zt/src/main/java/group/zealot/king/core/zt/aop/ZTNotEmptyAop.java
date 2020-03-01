package group.zealot.king.core.zt.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class ZTNotEmptyAop {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(group.zealot.king.core.zt.aop.ZTNotEmpty)")
    public void pointcut() {
    }


    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        joinPoint.getArgs();
    }
}
