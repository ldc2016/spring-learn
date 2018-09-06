package com.ldc.spring.core.enums;

import org.springframework.util.StringUtils;

/**
 * created by liudacheng on 2018/9/6.
 */
public enum ProductType implements StringValuedEnum {

    CASH_LOAN("借呗", "00001"),

    HOTEL_LOAN("艺龙贷", "00002"),

    CREDIT_PAY("花呗", "00003");

    private String name;
    private String productNo;

    ProductType(String name, String no) {
        this.name = name;
        this.productNo = no;
    }


    public String getNo() {
        return this.productNo;
    }

    public static ProductType parse(String no) {
        if (StringUtils.isEmpty(no)) {
            return null;
        }

        for (ProductType productNo : values()) {
            if (productNo.getNo().equals(no)) {
                return productNo;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "ProductNo{" +
                "name='" + name + '\'' +
                ", no='" + productNo + '\'' +
                '}';
    }

    @Override
    public String getValue() {
        return this.productNo;
    }
}
