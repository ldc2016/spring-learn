package com.ldc.spring.core.enums;

/**
 * created by liudacheng on 2018/9/6.
 */
public enum RepayType implements IntegerValuedEnum{

    CASHIER(1, "支付还款"),
    DEDUCT(2, "自动还款"),
    REFUND(3, "退款还款"),
    ;

    private Integer value;
    private String msg;

    RepayType(Integer code, String msg) {
        this.value = code;
        this.msg = msg;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }
}
