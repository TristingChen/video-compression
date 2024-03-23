package com.zhifan.listener;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.zhifan.constant.LogTemplate;
import com.zhifan.service.VideoInfoService;
import freemarker.template.utility.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author chenjialing
 */
@Slf4j
@Component
public class FileListener extends FileAlterationListenerAdaptor {



    @Autowired
    VideoInfoService videoInfoService;

    @Override
    public void onStart(FileAlterationObserver observer) {
        super.onStart(observer);
    }

    @Override
    public void onDirectoryCreate(File directory) {
        //不管
    }

    @Override
    public void onDirectoryChange(File directory) {
        //不管
    }

    @Override
    public void onDirectoryDelete(File directory) {
        //不管
    }

    @Override
    public void onFileCreate(File file) {
        //进行数据库任务的添加 文件格式需要是mp4结尾的 mdf的不需要处理
        log.info(LogTemplate.PROCESS_LOG_MSG_TEMPLATE,"文件监听","新增",file.getAbsolutePath());
        videoInfoService.saveOne(file);

    }

    @Override
    public void onFileChange(File file) {
        String compressedPath = file.getAbsolutePath();
        //不管
        log.info(LogTemplate.PROCESS_LOG_MSG_TEMPLATE,"文件监听","修改",file.getAbsolutePath());
    }

    @Override
    public void onFileDelete(File file) {
        String absolutePath = file.getAbsolutePath();
        log.info(LogTemplate.PROCESS_LOG_MSG_TEMPLATE,"文件监听","删除",absolutePath);
        //修改压缩中的状态 文件格式需要是mp4结尾的  mdf结尾的不处理
//        String typeByPath = FileTypeUtil.getTypeByPath(absolutePath);
//        System.out.println("文件类型:"+typeByPath);

    }

    @Override
    public void onStop(FileAlterationObserver observer) {
        super.onStop(observer);
        //不管
    }
}