package com.zhifan.listener;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author chenjialing
 */
@Component
public class FileMonitor {

    @Autowired
    FileListener fileListener;


    private static final FileMonitor instance = new FileMonitor();
    // 私有构造方法，防止外部直接实例化
    private FileMonitor() {
        // 初始化属性等操作
    }

    public static FileMonitor getInstance() {
        return instance;
    }


    public  void addDirectoryListener(FileAlterationMonitor monitor, String directory) {
        FileAlterationObserver observer = new FileAlterationObserver(new File(directory));
        observer.addListener(fileListener);
        monitor.addObserver(observer);
    }

    public  void removeDirectoryListener(FileAlterationMonitor monitor, String directory) {
        for (FileAlterationObserver observer : monitor.getObservers()) {
            if (observer.getDirectory().getPath().equals(directory)) {
                monitor.removeObserver(observer);
                break;
            }
        }
    }
}
