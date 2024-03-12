package com.zhifan.constant;

/**
 * @author Miracle
 * @date 2022/5/19 9:51
 */
public class LogTemplate {

    /**
     * 流程记录日志模板
     */
    public static final String PROCESS_LOG_TEMPLATE = "Service:{} -> Result:{}";

    /**
     * 流程记录模板，带消息返回
     */
    public static final String PROCESS_LOG_MSG_TEMPLATE = "Service:{} -> Result:{} -> Msg:{}";

    /**
     * 异常记录模板
     */
    public static final String ERROR_LOG_TEMPLATE = "Service:{} -> Result:{} -> Error:{}";

    /**
     * 异常记录模板，带数据体与异常信息
     */
    public static final String ERROR_LOG_MSG_TEMPLATE = "Service:{} -> Result:{} -> Msg:{} -> Error:{}";
}
