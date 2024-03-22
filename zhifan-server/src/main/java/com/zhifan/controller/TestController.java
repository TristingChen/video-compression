package com.zhifan.controller;

import com.zhifan.entity.VideoInfo;
import com.zhifan.response.CommonResponse;
import com.zhifan.service.VideoInfoService;
import com.zhifan.utils.JavaCvUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author chenjialing
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    @Autowired
    VideoInfoService videoInfoService;


    @Qualifier("taskExecutor")
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @GetMapping("/saveOne")
    public CommonResponse<Object> videoAreaList(){
        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setFileName("test");
//        videoInfo.setFilePath("d:123123");
        return CommonResponse.success(videoInfoService.save(videoInfo));
    }


    @GetMapping("/thread")
    public CommonResponse<Object> testThread() throws InterruptedException {

        for (int i = 0; i < 4; i++) {
            int finalI = i;
            taskExecutor.execute(()->{

                try {
                    JavaCvUtil.videoConvert("D:\\BaiduNetdiskDownload\\原始文件.MP4","./outfile"+ finalI +".mp4");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            taskExecutor.execute(()->{
                log.info("当前任务执行：--"+finalI);

            });
        }


        while (true) {
            System.out.println();

            int queueSize = taskExecutor.getQueueSize();
            System.out.println("当前排队线程数：" + queueSize);

            int activeCount = taskExecutor.getActiveCount();
            System.out.println("当前活动线程数：" + activeCount);

            long queueCapacity = taskExecutor.getQueueCapacity();
            System.out.println("执行完成线的空间：" + queueCapacity);

            long taskCount = taskExecutor.getThreadPoolExecutor().getTaskCount();
            System.out.println("总线程数：" + taskCount);

            long completedTaskCount = taskExecutor.getThreadPoolExecutor().getCompletedTaskCount();
            System.out.println("完成线程数：" + completedTaskCount);


            Thread.sleep(3000);
        }




    }
}
