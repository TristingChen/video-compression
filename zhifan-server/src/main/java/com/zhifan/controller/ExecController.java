package com.zhifan.controller;

import cn.hutool.core.bean.BeanUtil;
import com.zhifan.entity.FileConfig;
import com.zhifan.entity.VideoInfo;
import com.zhifan.response.CommonResponse;
import com.zhifan.service.FileConfigService;
import com.zhifan.service.VideoInfoService;
import com.zhifan.vo.FileConfigReq;
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
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    public Result<PageData<VideoInfo>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<VideoInfo> page = videoInfoService.selectPage(params);

        return new Result<PageData<VideoInfo>>().ok(page);
    }

    @PostMapping("/file-config/save")
    public CommonResponse save(@Valid @RequestBody FileConfigReq req){
        fileConfigService.saveOne(req);
        return CommonResponse.success();
    }

}
