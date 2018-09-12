package com.ldc.spring.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


public class ClassPathUtil {

    private final static Logger logger = LoggerFactory.getLogger(ClassPathUtil.class);

    public static String getCurrentPath() {
        Class<?> caller = getCaller();
        if (caller == null) {
            caller = ClassPathUtil.class;
        }
        return getCurrentPath(caller);
    }


    public static Class<?> getCaller() {
        StackTraceElement[] stack = (new Throwable()).getStackTrace();
        if (stack.length < 3) {
            return ClassPathUtil.class;
        }
        String className = stack[2].getClassName();
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.info("getCaller, 初始化类实例时发生异常，异常信息：{}",e.getMessage(),e);
        }
        return null;
    }

    public static String getCurrentPath(Class<?> cls) {
        String path = cls.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = path.replaceFirst("file:/", "");
        path = path.replaceAll("!/", "");

        logger.info("ClassPathUtil.getCurrentPath, before路径path: {}",path);
        if (path.lastIndexOf(File.separator) >= 0) {
            path = path.substring(0, path.lastIndexOf(File.separator));
        }

        // 找到JAR包所在的路径
        if(path.lastIndexOf(".jar") >=0){
            path = path.substring(0, path.lastIndexOf(".jar"));
        }

        if (path.lastIndexOf(File.separator) >= 0) {
            path = path.substring(0, path.lastIndexOf(File.separator));
        }

        path = "/" + path;
        logger.info("ClassPathUtil.getCurrentPath, after路径path: {}",path);
        return path;
    }

    public static String getLocalTestCurrentPath(Class<?> cls) {
        String path = cls.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = path.replaceFirst("file:/", "");
        path = path.replaceAll("!/", "");
        if (path.lastIndexOf(File.separator) >= 0) {
            path = path.substring(0, path.lastIndexOf(File.separator));
        }
        if ("/".equalsIgnoreCase(path.substring(0, 1))) {
            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.contains("window")) {
                path = path.substring(1);
            }
        }
        return path;
    }

    public static String getRelativePathForTest(){
        File file = new File(getCurrentPath());
        return file.getParent() + "/classes/";
    }

//    public static String getDesKeyRelativePath(){
//        File file = new File(ClassPathUtil.getCurrentPath());
//        return file.getPath() + "/deploy/";
//    }
}
