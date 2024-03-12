package com.zhifan.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Miracle
 * @date 2023/4/6 11:40
 */
@Getter
@AllArgsConstructor
public enum PtzType {

    //云镜控制
    MOVE_STOP(0),
    MOVE_LEFT(2),
    MOVE_RIGHT(1),
    MOVE_UP(8),
    MOVE_DOWN(4),
    MOVE_UP_LEFT(10),
    MOVE_UP_RIGHT(9),
    MOVE_DOWN_LEFT(6),
    MOVE_DOWN_RIGHT(5),
    //倍率
    ZOOM_IN(16),
    ZOOM_OUT(32),


    //预置位
    PRESET_SET(129),
    PRESET_INVOKE(130),
    PRESET_DEL(131),

    //F1 指令
    //光圈缩小放大
    IRIS_REDUCE(72),
    IRIS_GROW(68),
    //聚焦近远
    FOCUS_FAR(65),
    FOCUS_NEAR(66),
    //F1停止stop
    IRIS_AND_FOCUS_STOP(64);


    private final Integer code;

    /**
     * 判断类型是否存在
     * @param ptzCode
     * @return
     */
    public static boolean isExist(Integer ptzCode) {
        if (ptzCode == null) {
            return false;
        }
        for (PtzType ptzType : PtzType.values()) {
            if (ptzCode.equals(ptzType.getCode())){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断类型是否存在
     * @param ptzCode
     * @return
     */
    public static PtzType getPtzType(Integer ptzCode) {
        if (ptzCode == null) {
            return null;
        }
        for (PtzType ptzType : PtzType.values()) {
            if (ptzCode.equals(ptzType.getCode())){
                return ptzType;
            }
        }
        return null;
    }

}
