package com.zhifan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhifan.entity.VideoInfo;

import java.util.List;

/**
 * @author chenjialing
 */
public interface VideoInfoService extends IService<VideoInfo> {

    void saveOne(String absolutePath);

    void deleteOne(String absolutePath);

    List<VideoInfo> getUnStartTask(int num);

    void CompressListTask(List<VideoInfo> unStartTask);


}
