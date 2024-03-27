package com.zhifan.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhifan.constant.LogTemplate;
import com.zhifan.entity.FileConfig;
import com.zhifan.exception.BusinessErrorEnums;
import com.zhifan.exception.BusinessException;
import com.zhifan.listener.FileMonitor;
import com.zhifan.mapper.FileConfigMapper;
import com.zhifan.mapper.VideoInfoMapper;
import com.zhifan.service.FileConfigService;
import com.zhifan.service.VideoInfoService;
import com.zhifan.vo.FileConfigReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Stack;

/**
 * 文件的目录监听 以及进行文件信息入库，还有
 * @author chenjialing
 */
@Service
@Slf4j
public class FileConfigServiceImpl extends ServiceImpl<FileConfigMapper, FileConfig> implements FileConfigService {
    private static FileAlterationMonitor monitor;

    @Autowired
    FileMonitor fileMonitor;
    @PostConstruct
    private void init(){
        monitor = new FileAlterationMonitor(5000);
        try {
            monitor.start();

        }catch (Exception e){
            log.error(LogTemplate.ERROR_LOG_TEMPLATE,"文件监听初始启动",e);
        }

    }

    /**
     * 新增  默认数据库中无相关的数据
     * @param req
     */
    @Override
    public void saveOne(FileConfigReq req) {
        String globalFilePath = req.getGlobalFilePath();
        File file = new File(globalFilePath);

        if(!file.isDirectory()){
            throw new BusinessException(BusinessErrorEnums.DATA_IS_WRONG,"文件根目录不存在");
        }
        FileConfig fileConfig = new FileConfig();
        BeanUtil.copyProperties(req,fileConfig);
        fileConfig.setCreatedAt(LocalDateTime.now());
        fileConfig.setUpdatedAt(LocalDateTime.now());
        //入库
        this.save(fileConfig);
        monitorPath(globalFilePath);


    }

    @Override
    public void monitorPath(String path) {
        //进入文件变化的扫描
        fileMonitor.addDirectoryListener(monitor,path);
    }

    /**
     * 进行编辑，可能存在已有的转码文件任务入库
     * @param req
     * @param id
     */
    @Override
    public void editOne(FileConfigReq req, Long id) {
        //判断新旧目录是否一直
        FileConfig oneById = this.getById(id);
        if(!oneById.getGlobalFilePath().equals(req.getGlobalFilePath())){
            ////先取消原来的目录监听
            fileMonitor.removeDirectoryListener(monitor,oneById.getGlobalFilePath());
        }
        oneById.setUpdatedAt(LocalDateTime.now());
        BeanUtil.copyProperties(req,oneById);
        this.updateById(oneById);
        //重新监听
        monitorPath(req.getGlobalFilePath());
    }

    private  void search(File fileRoot) {
        // 创建一个栈用于存储目录
        Stack<File> stack = new Stack<>();
        stack.push(fileRoot);

        while (!stack.isEmpty()) {
            File currentDir = stack.pop();
            File[] fileList = currentDir.listFiles();

            if (fileList != null) {
                for (File file : fileList) {
                    if (file.isDirectory()) {
                        stack.push(file); // 将子目录压入栈中
                    } else {
                        // 处理文件，这里可以根据需求进行操作
                        System.out.println("File: " + file.getAbsolutePath());
                    }
                }
            }
        }
    }
}
