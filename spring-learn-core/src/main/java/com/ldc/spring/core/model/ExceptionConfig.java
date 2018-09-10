package com.ldc.spring.core.model;

import lombok.Data;

import java.util.Date;

/**
 * created by liudacheng on 2018/9/10.
 */
@Data
public class ExceptionConfig {
    /**
     * 主键
     */
    private Long id;

    /**
     * 异常类型
     */
    private String exceptionClass;

    /**
     * 异常信息
     */
    private String exceptionMessage;

    /**
     * 错误码
     */
    private int errorCode;

    /**
     * 是否打印异常堆栈信息
     */
    private boolean printExceptionStack;

    /**
     * 日志级别
     */
    private int logLevel;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
