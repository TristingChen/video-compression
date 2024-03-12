package com.zhifan.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * id数据类型
 * @author Miracle
 * @date 2023/1/17 14:48
 */
@Getter
@AllArgsConstructor
public enum IdType {

    GATEWAY(1),
    DEVICE(2),
    CHANNEL(3),

    ;

    private final Integer code;

    public static IdType getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (IdType idType : IdType.values()) {
            if (code.equals(idType.getCode())){
                return idType;
            }
        }
        return null;
    }
}
