package com.ldc.spring.enums;

public enum ResponseCode {
    SUCCESS("200", "操作成功"),
    FAIL("500", "系统异常");
    private String code;
    private String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}