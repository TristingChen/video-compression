package com.zhifan.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 过滤特殊符号
 * @author Miracle
 * @date 2022/4/28 15:04
 */
@Aspect
@Component
public class IllegalStringValidAsp {


    @Pointcut("@annotation(com.zhifan.aspect.annotation.IllegalStringValid)")
    public void illegalStringValid(){

    }

    @Around("illegalStringValid()")
    public Object blankStringConvertBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] methodArgs = joinPoint.getArgs();
        for (int i = 0; i < methodArgs.length; i++){
            Object data = methodArgs[i];
            if (data instanceof String && !StringUtils.isEmpty(data)){
                methodArgs[i] = methodArgs[i].toString().replaceAll("%", "/%");
            }
        }
        return joinPoint.proceed(methodArgs);
    }
}
