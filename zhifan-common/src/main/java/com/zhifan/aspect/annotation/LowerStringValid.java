package com.zhifan.aspect.annotation;

import java.lang.annotation.*;

/**
 * @author Miracle
 * @date 2022/5/30 17:09
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LowerStringValid {
}
