package com.zhifan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhifan.entity.VideoInfo;
import com.zhifan.mapper.VideoInfoMapper;
import com.zhifan.service.VideoInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VideoInfoServiceImpl  extends ServiceImpl<VideoInfoMapper, VideoInfo> implements VideoInfoService {

    @Override
    public void saveOne(VideoInfo videoInfo) {
        this.save(videoInfo);
    }
}
