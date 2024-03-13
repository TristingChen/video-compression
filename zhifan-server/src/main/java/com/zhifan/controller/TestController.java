package com.zhifan.controller;

import com.zhifan.entity.VideoInfo;
import com.zhifan.response.CommonResponse;
import com.zhifan.service.VideoInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenjialing
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    @Autowired
    VideoInfoService videoInfoService;


    @GetMapping("/saveOne")
    public CommonResponse<Object> videoAreaList(){
        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setFileName("test");
        videoInfo.setFilePath("d:123123");
        return CommonResponse.success(videoInfoService.save(videoInfo));
    }

}
