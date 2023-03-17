package linc.fun.dormitory.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yqlin
 * @date 2022/4/14 23:06
 * @description 用来检查是否需要带token
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenCheck {
    boolean required() default true;
}
