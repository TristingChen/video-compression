//package com.zhifan.response;
//
//import org.springframework.core.MethodParameter;
//import org.springframework.core.env.Environment;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
//
///**
// * @author Miracle
// * @date 2022/9/16 10:16
// */
//@RestControllerAdvice
//public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {
//
//    private static String[] excludeUrls;
//
//    private GlobalResponseHandler(Environment environment) {
//        String excludeUrl = environment.getProperty("response.exclude-url");
//        if (StringUtils.hasText(excludeUrl)) {
//            excludeUrls = excludeUrl.split(",");
//        } else {
//            excludeUrls = new String[0];
//        }
//    }
//
//
//    @Override
//    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
//        return true;
//    }
//
//    @Override
//    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
//        // 排除api文档的接口，这个接口不需要统一
//        for (String path : excludeUrls) {
//            if (request.getURI().getPath().startsWith(path)) {
//                return body;
//            }
//        }
//
//        if (body instanceof CommonResponse) {
//            return body;
//        }
//
//
//        return CommonResponse.success(body);
//    }
//}
