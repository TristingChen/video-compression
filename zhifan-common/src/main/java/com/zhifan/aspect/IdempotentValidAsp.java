package com.zhifan.aspect;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.zhifan.aspect.annotation.IdempotentValid;
import com.zhifan.constant.MarkConstant;
import com.zhifan.exception.BusinessErrorEnums;
import com.zhifan.exception.BusinessException;
import com.zhifan.utils.ReflectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 限次数访问
 * @author Miracle
 * @date 2022/6/17 16:07
 */
@Aspect
@Component
public class IdempotentValidAsp {

    @Autowired
    private HttpServletRequest httpServletRequest;


    volatile private  static LoadingCache<String, AtomicInteger> tokenCache = CacheBuilder.newBuilder()
            .maximumSize(1024)
            .build(new CacheLoader<String, AtomicInteger>() {
                @Override
                public AtomicInteger load(String key) throws Exception {
                    return new AtomicInteger(100);
                }
            });


    @Pointcut("@annotation(com.zhifan.aspect.annotation.IdempotentValid)")
    public void idempotentValid(){}

    @Around("idempotentValid() && @annotation(idempotentValidData) ")
    public Object idempotentValidAround(ProceedingJoinPoint joinPoint, IdempotentValid idempotentValidData) throws Throwable {
        // 判断允许访问次数是否在合适的范围内
        if (idempotentValidData.allowNum() > 100 || idempotentValidData.allowNum() < 1){
            throw new BusinessException(BusinessErrorEnums.VALID_ANNOTATION_PARAMETER_ERROR);
        }

        // 获取访问链接
        String key = httpServletRequest.getRequestURI();
        // 判断是否需要拦截指定字段
        if (Objects.nonNull(idempotentValidData.fieldName()) && !"".equals(idempotentValidData.fieldName())){
            Object methodArg = joinPoint.getArgs()[0];
            Object value = ReflectionUtils.getValueByFieldName(methodArg, idempotentValidData.fieldName());
            if (Objects.nonNull(value)){
                key = key + MarkConstant.MARK_SPLIT_SYMBOL + idempotentValidData.fieldName() + MarkConstant.MARK_SPLIT_SEMICOLON + value;
            }else {
                throw new BusinessException(BusinessErrorEnums.VALID_NOT_FOUNT_FIELD, "找不到对应的字段");
            }
        }
        int allowNum = 100 / idempotentValidData.allowNum() ;
        AtomicInteger atomicNum = tokenCache.get(key);

        if (atomicNum.addAndGet(-allowNum) < 0) {
            tokenCache.get(key).addAndGet(allowNum);
            throw new BusinessException(BusinessErrorEnums.VALID_REPETITIVE_OPERATION_ERROR, "请稍后再试");
        }
        Object proceed;
        try{
            proceed = joinPoint.proceed();
        }finally {
            atomicNum.addAndGet(allowNum);
        }
        return proceed;
    }
}
