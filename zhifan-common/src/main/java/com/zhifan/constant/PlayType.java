package com.zhifan.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Miracle
 * @date 2023/2/6 11:49
 */
@Getter
@AllArgsConstructor
public enum PlayType {

    LIVE(1, "LIVE"),

    RECORD(2, "RECORD"),
    ALARM(3, "ALARM"),
    DOWNLOAD(4, "DOWNLOAD"),

    CUSTOM_LIVE(5, "CUSTOM_LIVE"),
    AUDIO_LIVE(6, "AUDIO_LIVE"),
    ;

    private final Integer code;
    private final String msg;

    public static String getMsgByCode(int code){
        for (PlayType playType : PlayType.values()) {
            if (playType.getCode().equals(code)){
                return playType.getMsg();
            }
        }
        return null;
    }
}
