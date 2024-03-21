package com.zhifan.controller;

import cn.hutool.core.bean.BeanUtil;
import com.zhifan.entity.FileConfig;
import com.zhifan.response.CommonResponse;
import com.zhifan.service.FileConfigService;
import com.zhifan.vo.FileConfigReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author chenjialing
 */
@Slf4j
@RestController
@RequestMapping("/sys")
public class ExecController {

    @Autowired
    FileConfigService fileConfigService;

    @GetMapping("/file-config/list")
    public CommonResponse list() {

        return  CommonResponse.success(fileConfigService.list());
    }

    @PostMapping("/file-config/save")
    public CommonResponse save(@RequestBody FileConfigReq req){
        fileConfigService.saveOne(req);
        return CommonResponse.success();
    }

}
