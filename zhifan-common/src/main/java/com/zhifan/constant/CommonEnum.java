package com.zhifan.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Miracle
 * @date 2022/4/20 14:21
 */
@Getter
@AllArgsConstructor
public enum CommonEnum {

    DISABLE(0, "禁用"),
    ENABLE(1, "启用"),
    ;
    private final Integer code;

    private final String msg;

    public static Boolean getBoolean(Integer code){
        return !code.equals(DISABLE.getCode());
    }
}
