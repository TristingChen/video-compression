package com.zhifan.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhifan.constant.LogTemplate;
import com.zhifan.entity.FileConfig;
import com.zhifan.entity.VideoInfo;
import com.zhifan.mapper.FileConfigMapper;
import com.zhifan.mapper.VideoInfoMapper;
import com.zhifan.service.VideoInfoService;
import com.zhifan.utils.JavaCvUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @author chenjialing
 */
@Service
@Slf4j
public class VideoInfoServiceImpl  extends ServiceImpl<VideoInfoMapper, VideoInfo> implements VideoInfoService {
    private final String dealFileType = "mp4";

    @Qualifier("taskExecutor")
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private FileConfigMapper fileConfigMapper;

    @Autowired
    JavaCvUtil javaCvUtil;

    @Override
    public void saveOne(File file) {
        //数据库判断是否已存在  以及处理状态判断
        taskExecutor.execute(()->{
            String compressedPath = FileUtil.getAbsolutePath(file);
            String fileType = FileUtil.getType(file);
            //进行数据库任务的添加 文件格式需要是mp4结尾的 mdf的不需要处理
            if(fileType.equalsIgnoreCase(dealFileType)){

                String name = FileUtil.getName(file);
                if(StrUtil.contains(name,fileNameSuffix)){
                    //压缩文件新增不进行处理
                    return;
                }
                //查询文件信息表是否存在
                String absolutePath = FileUtil.getAbsolutePath(file);
                LambdaQueryWrapper<VideoInfo> videoInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
                videoInfoLambdaQueryWrapper.eq(VideoInfo::getFullFilePath,absolutePath);
                VideoInfo existOne = this.getOne(videoInfoLambdaQueryWrapper);
                if(ObjectUtil.isEmpty(existOne)){
                    //进行插入
                    List<FileConfig> fileConfigs = fileConfigMapper.selectList(null);
                    Long fileConfigId = 0L;
                    for (FileConfig fileConfig : fileConfigs){
                        if(absolutePath.contains(fileConfig.getGlobalFilePath())){
                            fileConfigId = fileConfig.getId();
                            break;
                        }
                    }

                    VideoInfo videoInfo = new VideoInfo();
                    videoInfo.setFileConfigId(fileConfigId);
                    videoInfo.setFullFilePath(absolutePath);
                    videoInfo.setFullFileDirectory(FileUtil.getParent(file,1).getAbsolutePath());
                    videoInfo.setFileSize(FileUtil.size(file));
                    videoInfo.setFileName(name);
                    this.save(videoInfo);

                }else {
                    //判断状态是否是0  非0情况，可能压缩后重新生成的 基本可以不用处理

                }
            }
        });
    }

    @Override
    public void deleteOne(File file) {
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
                String outFilePath = videoInfo.getFullFileDirectory()+File.separator+ fileNameSuffix+videoInfo.getFileName();

                try {
                    javaCvUtil.videoMp4Convert(fullFilePath, outFilePath,videoInfo);
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
