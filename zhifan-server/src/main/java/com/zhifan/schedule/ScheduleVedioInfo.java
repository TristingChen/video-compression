package com.zhifan.schedule;

import com.zhifan.config.ThreadPoolTaskConfig;
import com.zhifan.constant.LogTemplate;
import com.zhifan.entity.VideoInfo;
import com.zhifan.service.VideoInfoService;
import com.zhifan.utils.JavaCvUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author chenjialing
 */
@Component
@Slf4j
public class ScheduleVedioInfo {



    @Autowired
    private VideoInfoService videoInfoService;

    @Qualifier("taskExecutor")
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;



    /**
     * 每10s执行一次 将待转码的文件取出，进行转码处理； 但是如果线程池目前是满得，则暂时等待
     */
    @Scheduled(fixedRate=10000)
    public void taskExec(){
        int activeCount = taskExecutor.getActiveCount();
        if(ThreadPoolTaskConfig.cpuNum <= activeCount){
            log.info(LogTemplate.PROCESS_LOG_MSG_TEMPLATE,"定时任务执行","线程暂时繁忙","暂时不添加新的任务");
            return;
        }
        int newTaskCount = ThreadPoolTaskConfig.cpuNum - activeCount;
        List<VideoInfo> unStartTask = videoInfoService.getUnStartTask(newTaskCount);
        videoInfoService.CompressListTask(unStartTask);



    }
}
