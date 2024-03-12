package com.zhifan.utils;

import org.springframework.context.ApplicationContext;


/**
 * @author Miracle
 * @date 2023/3/28 10:04
 */
public class SpringContextUtils {

    private static ApplicationContext ac;

    public static <T>  T getBean(String beanName, Class<T> clazz) {
        return ac.getBean(beanName, clazz);
    }

    public static <T>  T getBean(Class<T> clazz) {
        return ac.getBean(clazz);
    }

    public static void setAc(ApplicationContext applicationContext) {
        ac = applicationContext;

    }
}
