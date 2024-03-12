package com.zhifan.utils;

import com.zhifan.exception.BusinessErrorEnums;
import com.zhifan.exception.BusinessException;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * 反射工具类
 * @author Miracle
 * @date 2022/6/17 18:10
 */
public class ReflectionUtils {


    /**
     * 根据字段名获取对象值
     * @param obj 对象
     * @param fieldName 字段名
     * @return
     * @throws BusinessException
     */
    public static Object getValueByFieldName(Object obj, String fieldName) throws BusinessException {
        if (Objects.isNull(obj)){
            return null;
        }
        if (StringUtils.isEmpty(fieldName)){
            return null;
        }
        Field field;
        try {
            field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new BusinessException(BusinessErrorEnums.VALID_PARAMETER_ERROR, e.getMessage());
        }
    }

}
