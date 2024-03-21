package com.zhifan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author chenjialing
 */
@Data
@TableName("video_info")
public class VideoInfo {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;


    /**
     * 全局的路径id
     */
    private Long fileConfigId;
    /**
     * 绝对路径
     */
    private String fullFilePath;
    /**
     * 绝对文件目录
     */
    private String fullFileDirectory;

    /**
     * 压缩后名称的全路径
     */
    private String fullCompressFilePath;

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
     * 转码花费的时间
     */
    private float compressDuration;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updatedAt;


}
