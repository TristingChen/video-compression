package com.zhifan.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 将空字符串转化为NULL
 * @author Miracle
 * @date 2022/4/28 15:04
 */
@Aspect
@Component
public class BlankStringValidAsp {


    @Pointcut("@annotation(com.zhifan.aspect.annotation.BlankStringValid)")
    public void blankStringConvert(){

    }

    @Around("blankStringConvert()")
    public Object blankStringConvertBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] methodArgs = joinPoint.getArgs();
        for (int i = 0; i < methodArgs.length; i++){
            Object data = methodArgs[i];
            if (data instanceof String && StringUtils.isEmpty(data)){
                methodArgs[i] = null;
            }
        }

        return joinPoint.proceed(methodArgs);

    }
}
