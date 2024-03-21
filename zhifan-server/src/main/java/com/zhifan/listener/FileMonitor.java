package com.zhifan.listener;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

/**
 * @author chenjialing
 */
public class FileMonitor {



    private static final FileMonitor instance = new FileMonitor();
    // 私有构造方法，防止外部直接实例化
    private FileMonitor() {
        // 初始化属性等操作
    }

    public static FileMonitor getInstance() {
        return instance;
    }


    public static void addDirectoryListener(FileAlterationMonitor monitor, String directory) {
        FileAlterationObserver observer = new FileAlterationObserver(new File(directory));
        observer.addListener(new FileListener());
        monitor.addObserver(observer);
    }

    public static void removeDirectoryListener(FileAlterationMonitor monitor, String directory) {
        for (FileAlterationObserver observer : monitor.getObservers()) {
            if (observer.getDirectory().getPath().equals(directory)) {
                monitor.removeObserver(observer);
                break;
            }
        }
    }
}
