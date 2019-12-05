package com.ldc.spring.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * created by liudacheng on 2018/4/19.
 */
public class FileUtil {

    public static final Logger LOG = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 创建文件夹
     * @param filePath
     * @return
     */
    public static void makeDir(String filePath){
        File dir = new File(filePath);
        //判断文件目录是否存在
        if (!dir.exists()) {
             dir.mkdirs();
        }
    }

    /**
     * 判断文件是否存在
     * @param filePath
     * @return
     */
    public static boolean isExist(String filePath, String fileName){
        File destFile = new File(filePath,fileName);
        return destFile.exists();
    }

//    public static String getCurrentPath() {
//        Class<?> caller = getCaller();
//        if (caller == null) {
//            caller = ClassPathUtil.class;
//        }
//        return getCurrentPath(caller);
//    }
}
