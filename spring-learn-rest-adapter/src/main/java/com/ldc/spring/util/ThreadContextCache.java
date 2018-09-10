package com.ldc.spring.util;

public class ThreadContextCache {
    public static final String IP = "_ip";
    public static final String USER_ID = "_user_id";
    public static final String USER_TOKEN = "_user_token";

    public static void setIp(String ip) {
        ThreadCacheUtil.setData(IP, ip);
    }

    public static String getIp() {
        return (String) ThreadCacheUtil.getData(IP);
    }

    public static void setUserId(Long userId) {
        ThreadCacheUtil.setData(USER_ID, userId);
    }

    public static Long getUserId() {
        return (Long) ThreadCacheUtil.getData(USER_ID);
    }

    public static void setUserToken(String token) {
        ThreadCacheUtil.setData(USER_TOKEN, token);
    }

    public static String getUserToken() {
        return (String) ThreadCacheUtil.getData(USER_TOKEN);
    }

}


