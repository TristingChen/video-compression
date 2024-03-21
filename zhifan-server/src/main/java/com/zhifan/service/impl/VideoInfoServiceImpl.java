package com.zhifan.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhifan.constant.LogTemplate;
import com.zhifan.entity.VideoInfo;
import com.zhifan.mapper.VideoInfoMapper;
import com.zhifan.service.VideoInfoService;
import com.zhifan.utils.JavaCvUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chenjialing
 */
@Service
@Slf4j
public class VideoInfoServiceImpl  extends ServiceImpl<VideoInfoMapper, VideoInfo> implements VideoInfoService {

    @Qualifier("taskExecutor")
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    private final String fileNameSuffix = "zhifan";
    @Override
    public void saveOne(String absolutePath) {
        //数据库判断是否已存在  以及处理状态判断


        //是否进行转码处理
    }

    @Override
    public void deleteOne(String absolutePath) {
        //判断是转码前 还是转码后的文件删除 或则是还没有进行转码的时候 就被系统自行删除了

    }

    @Override
    public List<VideoInfo> getUnStartTask(int num) {
        LambdaQueryWrapper<VideoInfo> videoInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        videoInfoLambdaQueryWrapper.eq(VideoInfo::getStatus,0);
        videoInfoLambdaQueryWrapper.last("limit "+num);
        return this.list(videoInfoLambdaQueryWrapper);

    }

    /**
     * 进行批量的文件的压缩操作
     * @param unStartTask
     */
    @Override
    public void CompressListTask(List<VideoInfo> unStartTask) {
        //任务修改为执行中
        unStartTask.forEach(videoInfo -> {

            videoInfo.setStatus(1);
            this.updateById(videoInfo);
        });

        unStartTask.forEach(videoInfo -> {
            taskExecutor.execute(()->{
                String fullFilePath = videoInfo.getFullFilePath();
                String outFilePath = videoInfo.getFullFileDirectory()+fileNameSuffix+videoInfo.getFileName();

                try {
                    VideoInfo videoInfo1 = JavaCvUtil.videoMp4Convert(fullFilePath, outFilePath);
                    BeanUtil.copyProperties(videoInfo1,videoInfo);
                    videoInfo.setStatus(2);
                    this.updateById(videoInfo);
                } catch (Exception e) {
                    log.error(LogTemplate.ERROR_LOG_TEMPLATE,"文件压缩--异常",fullFilePath,e);
                    videoInfo.setStatus(-1);
                    this.updateById(videoInfo);
                }
            });
        });
    }
}
