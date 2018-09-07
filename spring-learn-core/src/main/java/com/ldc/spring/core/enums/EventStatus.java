package com.ldc.spring.core.enums;

/**
 * created by liudacheng on 2018/9/7.
 */
public enum EventStatus implements IntegerValuedEnum{

    SUCCESS(1,"成功"),
    FAIL(2,"失败"),
    ;

    private int value;
    private String msg;

    EventStatus(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public static final EventStatus parseCode(int code) {
        for (EventStatus eventStatus:EventStatus.values()){
            if (eventStatus.value == code){
                return eventStatus;
            }
        }

        return null;
    }

}
