package linc.fun.dormitory.annotation;

import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * @author yqlin
 * @date 2022/4/14 22:19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@RestController
public @interface ResponseResult {
    /**
     * 判断是否可以忽略
     */
    boolean ignore() default false;
}
