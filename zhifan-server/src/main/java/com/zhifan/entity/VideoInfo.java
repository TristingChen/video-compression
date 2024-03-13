package com.zhifan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author chenjialing
 */
@Data
@TableName("video_info")
public class VideoInfo {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 文件目录
     */
    private String filePath;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 压缩状态
     * 0未开始，1进行中，2，成功，-1失败
     */
    private Integer status;
    /**
     * 异常的消息
     */
    private String msg;
    /**
     * 视频长度
     */
    private float duration;
    /**
     * 音视频通道个数
     */
    private Integer streamNum;
    /**
     * 文件大小
     */
    private float fileSize;
    /**
     * 压缩后文件大小
     */
    private float compressFileSize;

    private String createdAt;
    private String updatedAt;







}
