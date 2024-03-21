package com.zhifan.vo;

import lombok.Data;

/**
 * @author chenjialing
 */
@Data
public class CompressProcessVo {
    private Boolean flag = false;

    /**
     * 转码过程时长
     */
    private float compressDuration;
}
