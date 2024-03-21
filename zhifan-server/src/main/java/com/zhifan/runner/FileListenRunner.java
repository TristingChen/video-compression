package com.zhifan.runner;

import com.zhifan.constant.LogTemplate;
import com.zhifan.entity.FileConfig;
import com.zhifan.exception.BusinessErrorEnums;
import com.zhifan.exception.BusinessException;
import com.zhifan.service.FileConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * 启动，查询全局配置表是否有数据，如有直接进行监听,如无不需要管理
 * @author chenjialing
 */
@Component
@Slf4j
@Order(value = 2)
public class FileListenRunner implements CommandLineRunner {


    @Autowired
    private FileConfigService fileConfigService;

    @Override
    public void run(String... args) throws Exception {
        List<FileConfig> list = fileConfigService.list();
        list.forEach(one->{
            String globalFilePath = one.getGlobalFilePath();
            File file = new File(globalFilePath);

            if(!file.isDirectory()){
                log.error(LogTemplate.ERROR_LOG_TEMPLATE,"文件监听初始启动","文件根目录不存在:"+globalFilePath);
                return;
            }
            fileConfigService.monitorPath(one.getGlobalFilePath());
        });
    }
}
