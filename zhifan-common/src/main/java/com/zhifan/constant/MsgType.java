package com.zhifan.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息类型
 * @author Miracle
 * @date 2023/1/12 9:43
 */

@Getter
@AllArgsConstructor
public enum MsgType {

    /****************** 网关相关消息 ******************/
    GATEWAY_SIGN_IN("GATEWAY_SIGN_IN", false),
    GATEWAY_RE_SIGN_IN("GATEWAY_RE_SIGN_IN", true),
    GATEWAY_HEARTBEAT("GATEWAY_HEARTBEAT", false),


    /****************** 调度相关消息 ******************/

    DISPATCH_SIGN_IN("DISPATCH_SIGN_IN", false),

    DISPATCH_RE_SIGN_IN("DISPATCH_RE_SIGN_IN", true),

    DISPATCH_HEARTBEAT("DISPATCH_HEARTBEAT", false),

    /****************** 设备相关消息 ******************/

    DEVICE_TOTAL_SYNC("DEVICE_TOTAL_SYNC", true),
    DEVICE_SIGN_IN("DEVICE_SIGN_IN", true),
    DEVICE_SYNC("DEVICE_SYNC", true),
    DEVICE_ADD("DEVICE_ADD", false),
    DEVICE_DELETE_SOFT("DEVICE_DELETE_SOFT", false),
    DEVICE_DELETE_HARD("DEVICE_DELETE", false),

    DEVICE_DELETE_RECOVER("DEVICE_DELETE_RECOVER", false),
    /****************** 通道相关消息 ******************/
    CHANNEL_SYNC("CHANNEL_SYNC", true),
    CHANNEL_PTZ_CONTROL("CHANNEL_PTZ_CONTROL", false),
    CHANNEL_PTZ_PRESET("CHANNEL_PTZ_PRESET", false),
    CHANNEL_PTZ_3D("CHANNEL_PTZ_3D", false),
    CHANNEL_RECORD_INFO("CHANNEL_RECORD_INFO", false),
    CHANNEL_DELETE_HARD("CHANNEL_DELETE_HARD", false),
    CHANNEL_DELETE_SOFT("CHANNEL_DELETE_SOFT", false),
    CHANNEL_DELETE_RECOVER("CHANNEL_DELETE_RECOVER", false),
    CHANNEL_DEFENSES_DEPLOY("CHANNEL_DEFENSES_DEPLOY", false),
    CHANNEL_DEFENSES_WITHDRAW("CHANNEL_DEFENSES_WITHDRAW", false),
    /****************** 流媒体管理服务相关消息 ******************/
    STREAM_PLAY_RESULT("STREAM_PLAY_RESULT", true),
    STREAM_CLOSE("STREAM_CLOSE", false),
    STREAM_PLAY_STOP("STREAM_PLAY_STOP", false),
    STREAM_RECORD_START("STREAM_RECORD_START", false),
    STREAM_RECORD_STOP("STREAM_RECORD_STOP", false),
    STREAM_RECORD_SPEED("STREAM_RECORD_SPEED", false),
    STREAM_RECORD_SEEK("STREAM_RECORD_SEEK", false),
    STREAM_RECORD_PAUSE("STREAM_RECORD_PAUSE", false),
    STREAM_RECORD_RESUME("STREAM_RECORD_RESUME", false),
    STREAM_CHECK_RECORD("STREAM_CHECK_RECORD", true),
    STREAM_CHECK_STREAM("STREAM_CHECK_STREAM", true),
    STREAM_STOP_ALL("STREAM_STOP_ALL", false),
    STREAM_MEDIA_INFO("STREAM_MEDIA_INFO", true),
    STREAM_LIVE_PLAY_START("STREAM_LIVE_PLAY_START", true),
    STREAM_CUSTOM_LIVE_START("STREAM_CUSTOM_LIVE_START", true),
    STREAM_RECORD_PLAY_START("STREAM_RECORD_PLAY_START", false),
    STREAM_RECORD_DOWNLOAD("STREAM_RECORD_DOWNLOAD", false),
    STREAM_PICTURE_DOWNLOAD("STREAM_PICTURE_DOWNLOAD", false),
    STREAM_WEBRTC_AUDIO("STREAM_WEBRTC_AUDIO", false),
    ALARM_MSG_NOTIFICATION("ALARM_MSG_NOTIFICATION", false),
    STREAM_LIVE_PUSH("STREAM_LIVE_PUSH",  false),
    STREAM_RECORD_PUSH("STREAM_RECORD_PUSH", false),
    STREAM_PUSH_STOP("STREAM_PUSH_STOP", false);

    private final String msg;

    private final Boolean isMerge;

    public static MsgType getByStr(String msgStr) {
        if (msgStr == null) {
            return null;
        }
        for (MsgType msgType : MsgType.values()) {
            if (msgStr.equals(msgType.getMsg())){
                return msgType;
            }
        }
        return null;
    }



}
