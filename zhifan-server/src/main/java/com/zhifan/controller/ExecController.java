package com.zhifan.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.zhifan.entity.FileConfig;
import com.zhifan.entity.VideoInfo;
import com.zhifan.response.CommonResponse;
import com.zhifan.service.FileConfigService;
import com.zhifan.service.VideoInfoService;
import com.zhifan.vo.FileConfigReq;
import com.zhifan.vo.VideoInfoResp;
import io.renren.common.constant.Constant;
import io.renren.common.page.PageData;
import io.renren.common.utils.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author chenjialing
 */
@Slf4j
@RestController
@RequestMapping("/sys")
public class ExecController {

    @Autowired
    FileConfigService fileConfigService;

    @Autowired
    VideoInfoService videoInfoService;
    @GetMapping("/file-config/list")
    public CommonResponse list() {

        return  CommonResponse.success(fileConfigService.list());
    }

    @GetMapping("/video-info/page")

    public Result<PageData<VideoInfoResp>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<VideoInfoResp> page = videoInfoService.selectPage(params);

        return new Result<PageData<VideoInfoResp>>().ok(page);
    }

    @GetMapping("/file-config/getOne/{id}")
    public CommonResponse getOne(@PathVariable("id") Long id){
        return CommonResponse.success(fileConfigService.getById(id));
    }


    @PostMapping("/file-config/save")
    public CommonResponse save(@Valid @RequestBody FileConfigReq req){
        fileConfigService.saveOne(req);

        return CommonResponse.success();
    }

    @PutMapping("/file-config/save")
    public CommonResponse edit(@Valid @RequestBody FileConfigReq req){

        fileConfigService.editOne(req,req.getId());
        return CommonResponse.success();
    }

}
