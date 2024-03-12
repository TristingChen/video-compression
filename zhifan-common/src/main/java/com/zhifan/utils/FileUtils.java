package com.zhifan.utils;

import com.zhifan.constant.LogTemplate;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author Miracle
 * @date 2023/9/20 11:10
 */
@Slf4j
public class FileUtils {

    /**
     * 保存文件在本地其他目录
     *
     * @param file
     * @param localDir
     */
    public static void saveFileByLocal(File file, String localDir) {
        File newFileDir = new File(localDir);
        if (!file.exists()) {
            log.error(LogTemplate.ERROR_LOG_MSG_TEMPLATE,"保存文件到本地服务", "保存失败", file.getAbsolutePath(), "文件不存在");
            return;
        }
        if (file.isDirectory()) {
            log.error(LogTemplate.ERROR_LOG_MSG_TEMPLATE,"保存文件到本地服务", "保存失败", file.getAbsolutePath(), "移动的是目录，无法移动");
            return;
        }

        if (!newFileDir.exists()) {
            boolean mkdirs = newFileDir.mkdirs();
            if (!mkdirs){
                log.warn(LogTemplate.PROCESS_LOG_MSG_TEMPLATE, "保存文件到本地服务","要移动到目标位置文件的父目录不存在，创建文件目录失败" , newFileDir.getAbsolutePath());
            }else {
                log.warn(LogTemplate.PROCESS_LOG_MSG_TEMPLATE, "保存文件到本地服务","要移动到目标位置文件的父目录不存在，创建文件目录" , newFileDir.getAbsolutePath());
            }
        }

        File newFile = new File(localDir);
        try {
            copyFileUsingFileChannels(file, newFile);
        } catch (IOException e) {
            log.error(LogTemplate.ERROR_LOG_MSG_TEMPLATE,"保存文件到本地服务", "保存失败", file.getAbsolutePath(), e);
        }
    }

    public static void copyFileUsingFileChannels(File source, File dest) throws IOException {
        try(FileChannel inputChannel = new FileInputStream(source).getChannel();
            FileChannel outputChannel= new FileOutputStream(dest).getChannel()) {
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } catch (Exception ex){
            log.warn(LogTemplate.PROCESS_LOG_MSG_TEMPLATE, "保存文件到本地服务","保存文件失败" , ex);
        }finally {
            source.delete();
        }
    }
}
