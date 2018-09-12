package com.ldc.spring.utils;

import com.amazonaws.util.Base64;

/**
 *
 * BASE64编码解码工具包
 */
public class Base64Util {

    /**
     * BASE64字符串解码为二进制数据
     * @param base64
     * @return
     */
    public static byte[] decode(String base64) {
        return Base64.decode(base64.getBytes());
    }

    /**
     * 二进制数据编码为BASE64字符串 
     * @param bytes
     * @return
     */
    public static String encode(byte[] bytes) {
        return new String(Base64Util.encode(bytes));
    }

    /**
     * 由风控组提供
     * BASE64加密
     * @param key
     * @return
     */
    public static String encryptBASE64(byte[] key) {
        return org.apache.commons.codec.binary.Base64.encodeBase64String(key);
    }
}