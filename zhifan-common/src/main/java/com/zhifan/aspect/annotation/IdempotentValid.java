package com.zhifan.aspect.annotation;

import java.lang.annotation.*;

/**
 * @author Miracle
 * @date 2022/6/17 16:06
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IdempotentValid {

    /**
     * 标志参数
     * @return
     */
    String fieldName() default "";

    /**
     * 最大值为100， 最小为1
     */
    int allowNum() default 1;

}
