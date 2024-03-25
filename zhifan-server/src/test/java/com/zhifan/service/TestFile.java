package com.zhifan.service;

import com.zhifan.entity.VideoInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class TestFile {

    @Autowired
    VideoInfoService videoInfoService;

    @Test
    public void saveTest(){

//        VideoInfo videoInfo = new VideoInfo();
//        videoInfo.setFileName("test");
//        videoInfo.setFilePath("d:123123");
//        videoInfoService.save(videoInfo);


    }
}
