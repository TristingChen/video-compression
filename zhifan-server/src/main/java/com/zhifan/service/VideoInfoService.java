package com.zhifan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhifan.entity.VideoInfo;

/**
 * @author chenjialing
 */
public interface VideoInfoService extends IService<VideoInfo> {

    void saveOne(VideoInfo videoInfo);
}
