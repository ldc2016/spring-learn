package com.ldc.spring.core.enums;

/**
 * created by liudacheng on 2018/9/4.
 */
public enum OperationType {

    CREATE(1, "创建操作"),
    UPDATE(2, "更新操作"),
    READ(3, "查询操作"),
    DELETE(4, "删除操作"),
    ;

    private int value;
    private String msg;

    private OperationType(int value, String msg){
        this.value = value;
        this.msg = msg;
    }

    public int getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }
}
