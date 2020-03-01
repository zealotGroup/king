package group.zealot.king.core.zt.aop;


import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZTNotEmpty {
    String msg();
}
