package com.zhifan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhifan.entity.FileConfig;
import com.zhifan.entity.VideoInfo;
import com.zhifan.vo.FileConfigReq;

/**
 * @author chenjialing
 */
public interface FileConfigService extends IService<FileConfig> {


    void saveOne(FileConfigReq req);


    void monitorPath(String path);

    void editOne(FileConfigReq req,Long id);

}
