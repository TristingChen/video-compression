package com.zhifan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhifan.entity.VideoInfo;

import java.io.File;
import java.util.List;

/**
 * @author chenjialing
 */
public interface VideoInfoService extends IService<VideoInfo> {

    public final String fileNameSuffix = "zhifan";
    void saveOne(File file);

    void deleteOne(File file);

    List<VideoInfo> getUnStartTask(int num);

    void CompressListTask(List<VideoInfo> unStartTask);


}
