package com.zhifan.validator.constraints.annotation;


import com.zhifan.validator.constraints.NotSpecialCharValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 过滤特殊字符
 *
 * @author Miracle
 * @date 2022/6/6 9:33
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NotSpecialCharValidator.class})
public @interface NotSpecialChar {

    String[] except() default {};

    String message() default "This String have special char";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
