package com.ldc.spring.core.exception;

/**
 * created by liudacheng on 2018/9/5.
 */
public class BeanValidateException extends RuntimeException{
    public BeanValidateException(String message) {
        super(message);
    }
    public BeanValidateException(String message, Throwable t) {
        super(message, t);
    }
}
