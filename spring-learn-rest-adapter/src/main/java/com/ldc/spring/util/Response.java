package com.ldc.spring.util;

import com.ldc.spring.enums.ResponseCode;

public class Response<T> {

    private String code;
    private String message;
    private T data;

    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.setCode(ResponseCode.SUCCESS.getCode());
        response.setMessage(ResponseCode.SUCCESS.getMessage());
        response.setData(data);
        return response;
    }

    public static <T> Response<T> success() {
        return success(null);
    }


    public static <T> Response<T> fail(String errorMessage) {
        Response<T> response = new Response<>();
        response.setCode(ResponseCode.FAIL.getCode());
        response.setMessage(errorMessage);
        return response;
    }

    public static <T> Response<T> fail(String errorMessage,String code) {
        Response<T> response = new Response<>();
        response.setCode(code);
        response.setMessage(errorMessage);
        return response;
    }

    public static <T> Response<T> fail(T data) {
        Response<T> response = new Response<>();
        response.setCode(ResponseCode.FAIL.getCode());
        response.setMessage(ResponseCode.FAIL.getMessage());
        response.setData(data);
        return response;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}