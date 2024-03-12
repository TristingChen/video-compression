package com.zhifan.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 将字符串转为小写
 * @author Miracle
 * @date 2022/5/30 17:10
 */
@Aspect
@Component
public class LowerStringValidAsp {

    @Pointcut("@annotation(com.zhifan.aspect.annotation.LowerStringValid)")
    public void lowerStringValid(){

    }

    @Around("lowerStringValid()")
    public Object blankStringConvertBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] methodArgs = joinPoint.getArgs();
        for (int i = 0; i < methodArgs.length; i++){
            Object data = methodArgs[i];
            if (data instanceof String && !StringUtils.isEmpty(data)){
                methodArgs[i] = methodArgs[i].toString().toLowerCase();
            }
        }
        return joinPoint.proceed(methodArgs);

    }
}
