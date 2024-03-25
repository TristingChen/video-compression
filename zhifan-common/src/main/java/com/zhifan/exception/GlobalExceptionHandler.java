package com.zhifan.exception;


import com.zhifan.constant.LogTemplate;
import com.zhifan.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Miracle
 * @date 2020/2/19 15:10
 */
@Slf4j
//@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public CommonResponse doError(HttpServletResponse response, Exception ex) {
        if (ex instanceof BusinessException) {
            BusinessException businessException = (BusinessException) ex;
            log.error(LogTemplate.ERROR_LOG_MSG_TEMPLATE, "全局异常捕获", "业务异常", businessException.getErrDetail(), businessException.getBusinessErrorEnums());
            response.setStatus(businessException.getState());
            return CommonResponse.failure(businessException.getBusinessErrorEnums(), businessException.getErrDetail());
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
            HttpRequestMethodNotSupportedException noEx = (HttpRequestMethodNotSupportedException) ex;
            log.error(LogTemplate.ERROR_LOG_MSG_TEMPLATE, "全局异常捕获", "404异常", noEx.getMethod() + "-" + noEx.getSupportedMethods(), noEx.getMessage());
            response.setStatus(BusinessErrorEnums.VALID_METHOD_NOT_SUPPORTED.getState());
            return CommonResponse.failure(BusinessErrorEnums.VALID_METHOD_NOT_SUPPORTED);
        } else if (ex instanceof NoHandlerFoundException) {
            NoHandlerFoundException noEx = (NoHandlerFoundException) ex;
            log.error(LogTemplate.ERROR_LOG_MSG_TEMPLATE, "全局异常捕获", "404异常", noEx.getHttpMethod() + "-" + noEx.getRequestURL(), noEx.getMessage());
            response.setStatus(BusinessErrorEnums.VALID_NO_HANDLER_FOUND.getState());
            return CommonResponse.failure(BusinessErrorEnums.VALID_NO_HANDLER_FOUND);
        } else if (ex instanceof ServletRequestBindingException) {
            ServletRequestBindingException srEx = (ServletRequestBindingException) ex;
            log.error(LogTemplate.ERROR_LOG_MSG_TEMPLATE, "全局异常捕获", "ServletRequestBindingException异常", null, srEx);
            response.setStatus(BusinessErrorEnums.VALID_BIND_EXCEPTION_ERROR.getState());
            return CommonResponse.failure(BusinessErrorEnums.VALID_BIND_EXCEPTION_ERROR);
        } else {
            log.error(LogTemplate.ERROR_LOG_MSG_TEMPLATE, "全局异常捕获", "未知异常", null, ex);
            response.setStatus(BusinessErrorEnums.UNKNOWN_ERROR.getState());
            return CommonResponse.failure(BusinessErrorEnums.UNKNOWN_ERROR, ex.getMessage());
        }
    }
}
