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
     * 获得指定文件的byte数组
     */
    public static byte[] getBytes(String filePath) {

        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;

        byte[] buffer = null;
        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            LOG.error("FileUtil.getBytes, 异常信息FileNotFoundException:{} ",e.getMessage());
        } catch (IOException e) {
            LOG.error("FileUtil.getBytes, 异常信息IOException: {} ",e.getMessage());
        }finally {
            if(bos != null){
                try {
                    bos.close();
                } catch (IOException e) {
                    LOG.error("close bos error,异常信息:{}",e.getMessage());
                }
            }
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    LOG.error("close fis error异常信息:{}",e.getMessage());
                }
            }
        }
        return buffer;
    }

    /**
     * 根据byte数组，生成文件
     */
    public static void createFile(byte[] bfile, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists()) {//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath, fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            LOG.error("createFile error 异常信息:{}",e.getMessage());
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    LOG.error("close bos error 异常信息:{}",e1.getMessage());
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    LOG.error("close fos error 异常信息:{}",e1.getMessage());
                }
            }
        }
    }

    /**
     * 文件不存在时创建文件
     * @param filePath
     * @param fileName
     * @return
     * @throws IOException
     */
    public static File makeFileWhenNotExists(String filePath, String fileName) {
        File file = new File(filePath, fileName);
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();

            }
            if (!file.exists()) {
                file.createNewFile();
            }

        } catch (IOException e) {
            LOG.error("makeFileWhenNotExists error 异常信息:{}",e.getMessage());
        }
        return file;
    }

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
     * 删除指定路径下的文件
     * @param filePath
     * @param fileName
     * @return
     */
    public static boolean deleteFile(String filePath, String fileName){
        try {
            File file = new File(filePath, fileName);
            if (file.exists()) {
                if (file.isFile()) {
                    if (file.delete())
                        return true;
                } else {
                    LOG.info("[FileUtil.deleteFile]  路径：{} 下的文件：{} 不是文件类型！", filePath, fileName);
                    return false;
                }
            } else {
                LOG.info("[FileUtil.deleteFile]  路径：{} 下的文件：{} 不存在！", filePath, fileName);
            }
            return false;

        } catch (Exception e) {
            LOG.error("[FileUtil.deleteFile]  删除路径：{} 下的文件：{} 时发生异常：{}！", filePath, fileName, e.getMessage());
            return false;
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

}
