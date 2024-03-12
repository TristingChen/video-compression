package com.zhifan.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Miracle
 * @date 2020/2/19 15:07
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -7072123996206431364L;

    /**
     * 请求状态
     */
    private final Integer state;

    /**
     * 通用异常
     */
    private final BusinessErrorEnums businessErrorEnums;

    /**
     * 详细异常消息
     */
    private final Object errDetail;

    public BusinessException(Object errDetail) {
        super();
        this.state = 500;
        this.businessErrorEnums = BusinessErrorEnums.UNKNOWN_ERROR;
        this.errDetail = errDetail;
    }

    public BusinessException(BusinessErrorEnums businessErrorEnums) {
        super();
        this.state = businessErrorEnums.getState();
        this.businessErrorEnums = businessErrorEnums;
        this.errDetail = null;
    }

    public BusinessException(BusinessErrorEnums businessErrorEnums, String errDetail) {
        super();
        this.state = businessErrorEnums.getState();
        this.errDetail = errDetail;
        this.businessErrorEnums = businessErrorEnums;
    }
}
