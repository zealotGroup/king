package group.zealot.king.core.zt.aop;


import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZTValid {
    boolean NotNull() default false;// Object

    boolean NotEmpty() default false;// List

    boolean NotBlank() default false;// String

    int length() default 0;// String List

    String msg() default "";
}
