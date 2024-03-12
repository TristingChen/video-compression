package com.zhifan.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 注册类型
 * @see com.runjian.device.entity.DeviceInfo#deviceType
 * @see com.runjian.device.entity.ChannelInfo#channelType
 * @author Miracle
 * @date 2023/1/9 18:04
 */
@Getter
@AllArgsConstructor
public enum SignState {

    SUCCESS(0, "已注册"),
    TO_BE_ADD(1,"待添加"),

    TO_BE_SIGN_IN(2, "待注册"),

    DELETED(-1, "已删除")
    ;


    private final Integer code;

    private final String msg;
}
