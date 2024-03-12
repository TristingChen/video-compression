package com.zhifan.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Miracle
 * @date 2020/3/3 22:49
 */
@Getter
@AllArgsConstructor
@ToString
public enum BusinessErrorEnums {

    /**************************** 通用消息0与-1 ****************************/
    SUCCESS(200,0,"SUCCESS"),

    UNKNOWN_ERROR(500,-1,"SYSTEM_ERROR"),



    /**************************** 数据校验类异常,PREFIX:VALID CODE:10xxx ****************************/

    VALID_NO_OBJECT_FOUND(400,10001, "请求对象不存在"),

    VALID_NO_HANDLER_FOUND(400,10002,"找不到执行的路径操作"),

    VALID_BIND_EXCEPTION_ERROR(400,10003, "请求参数错误"),

    VALID_METHOD_NOT_SUPPORTED(400, 10004, "不支持的请求方式"),

    VALID_PARAMETER_ERROR(400,10005,"请求参数校验失败"),

    VALID_OBJECT_IS_EXIST(400, 10006, "对象已存在"),

    VALID_JSON_PROCESSING_ERROR(500, 10007, "JSON转化异常"),

    VALID_NOT_FOUNT_FIELD(500, 10008, "找不到对应的字段"),

    VALID_ANNOTATION_PARAMETER_ERROR(500, 10009, "注解参数错误"),

    VALID_ILLEGAL_OPERATION(400, 10010, "非法的操作"),

    VALID_REPETITIVE_OPERATION_ERROR(400, 10011, "短时间重复的操作"),

    VALID_REQUEST_TIME_OUT(500,10012,"请求超时"),

    /**************************** FEIGN第三方访问相关,PREFIX:FEIGN CODE:15xxx ****************************/

    FEIGN_REQUEST_BUSINESS_ERROR(500, 15000, "第三方系统业务请求异常"),

    FEIGN_REQUEST_TIME_OUT(500, 15001, "第三方系统业务请求超时"),


    /**************************** 用户模块相关异常,PREFIX:USER CODE:20xxx ****************************/

    USER_LOGIN_ERROR(401, 20001, "用户登录失败"),

    USER_NO_AUTH(401, 20002, "用户无权限"),


    TOKEN_IS_EXPIRE(401, 20003, "访问令牌过期"),
    USER_LOGIN_FAILURE(401, 20004, "用户已经退出或者在其他地方登录"),
    USER_FORBID_ACCESS(403, 20005, "用户无法访问"),
    /**************************** 定时器模块相关错误,PREFIX:TIMER CODE:21xxx ****************************/

    TIMER_START_ERROR(500, 21001, "定时任务启动异常"),

    TIMER_STOP_ERROR(500, 21002, "定时任务关闭异常"),

    TIMER_RESUME_ERROR(500, 21003, "定时任务恢复异常"),

    TIMER_RUN_ERROR(500,21004, "定时任务执行失败"),

    TIMER_ADD_ERROR(400, 21005, "添加定时任务失败"),

    TIMER_SAVE_TO_JSON_ERROR(500, 21006,  "定时器任务持久化失败"),

    TIMER_SELECT_ERROR(500, 21007, "定时器任务查询失败"),

    TIMER_DELETE_ERROR(500, 21008, "定时器删除异常"),

    /**************************** MQ模块相关异常,PREFIX:MQ CODE:22xxx ****************************/

    MQ_UNKNOWN_EXCHANGE_TYPE(500, 22001, "未知的消息队列类型"),

    MQ_QUEUE_IS_NOT_FOUND(500, 22002, "找不到该queue"),

    MQ_EXCHANGE_IS_NOT_FOUND(500, 22003, "找不到该exchange"),

    MQ_CONTAINER_NOT_FOUND(500, 60007, "找不到对应的container"),



    /**************************** 业务类型异常,PREFIX:模块名 CODE:3xxxx ****************************/

    ACCOUNT_NOT_ENABLED(400,30001, "该用户已被禁用"),

    REQUEST_EXPIRED(400,30002, "该请求鉴权超时，请重新生成鉴权参数"),

    AUTHORIZED_FAILED(400,30003, "鉴权失败"),

    NOT_FOUND_MEDIA_SERVER(500, 30005, "找不到可用的ZLM"),

    RECORD_PLAN_IS_DISABLE(500, 30006, "录像计划状态为禁用"),

    DB_DEVICE_NOT_OFFLINE(400,30008,"在线设备不允许删除"),

    SSE_CONNECT_ERROR(400, 30010, "SSE用户连接异常"),

    DEFAULT_MEDIA_DELETE_ERROR(400, 30011, "默认配置节点不可删除"),

    PRESETID_NOT_FOUND(400, 30012, "通道预置位信息不存在，请先查询"),



    /****************************  数据错误：50001-599999 Start ****************************/

    /**
     * 数据未找到
     */
    RESULT_DATA_NONE(404,50001, "数据未找到"),
    /**
     * 数据有误
     */
    DATA_IS_WRONG(400,50002, "数据有误"),
    /**
     * 数据已存在
     */
    DATA_ALREADY_EXISTED(400,50003, "数据已存在"),
    /**
     * 图片类型错误
     */
    IMAGE_IS_WRONG(400,50004, "仅支持 png jpg jpeg gif bmp webp 类型的图片"),
    /**
     * 数据过大
     */
    DATA_IS_LARGE(400,50005, "数据过大"),

    /****************************  数据错误：50001-599999 End ****************************/

    /**************************** 接口错误：60001-69999 Start ****************************/

    /**
     * 内部系统接口调用异常
     */
    INTERFACE_INNER_INVOKE_ERROR(500,60001, "内部系统接口调用异常"),
    /**
     * 外部系统接口调用异常
     */
    INTERFACE_OUTER_INVOKE_ERROR(500,60002, "外部系统接口调用异常"),
    /**
     * 该接口禁止访问
     */
    INTERFACE_FORBID_VISIT(500,60003, "该接口禁止访问"),
    /**
     * 接口地址无效
     */
    INTERFACE_ADDRESS_INVALID(500,60004, "接口地址无效"),
    /**
     * 接口请求超时
     */
    INTERFACE_REQUEST_TIMEOUT(500,60005, "接口请求超时"),
    /**
     * 接口负载过高
     */
    INTERFACE_EXCEED_LOAD(500,60006, "接口负载过高"),

    FEIGN_REQUEST_EXCEPTION(500,60007, "内部服务请求异常"),

    FEIGN_TIMEOUT_EXCEPTION(500,60008, "内部服务请求超时"),

    FEIGN_CANNOT_FIND_EXCEPTION(500,60009, "内部服务无法访问"),





;

    /**
     * 状态
     */
    private final Integer state;

    /**
     * 错误码
     */
    private final Integer errCode;

    /**
     * 错误信息
     */
    private final String errMsg;
}
