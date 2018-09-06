package com.ldc.spring.core.model;

import com.ldc.spring.core.enums.ProductType;
import com.ldc.spring.core.enums.RepayType;
import lombok.Data;

/**
 * created by liudacheng on 2018/4/12.
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer sex;
    private String mobilePhone;
    private String idNo;
    private RepayType repayType;
    private ProductType productType;

    public User() {
    }

    public User(String name, Integer sex, String mobilePhone, String idNo) {
        this.name = name;
        this.sex = sex;
        this.mobilePhone = mobilePhone;
        this.idNo = idNo;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", idNo='" + idNo + '\'' +
                '}';
    }
}
