package com.zhifan.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author chenjialing
 */
@Data
public class FileConfigReq {


    @NotNull
    private String globalFilePath;

    private String desc;

    @NotNull
    private LocalDate startDay = LocalDate.now();















}
