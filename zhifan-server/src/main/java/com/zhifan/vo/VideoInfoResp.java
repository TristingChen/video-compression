package com.zhifan.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author chenjialing
 */
@Data
@TableName("video_info")
public class VideoInfoResp {
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
     * 文件名称
     */
    private String fileName;

    /**
     * 压缩状态
     * 0未开始，1进行中，2，成功，-1失败
     */
    private String statusName;
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

    /**
     * 压缩比例
     */
    private String compressRatio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)		// 反序列化
    @JsonSerialize(using = LocalDateTimeSerializer.class)		// 序列化
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)		// 反序列化
    @JsonSerialize(using = LocalDateTimeSerializer.class)		// 序列化
    private LocalDateTime updatedAt;


}
