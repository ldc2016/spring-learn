package com.ldc.spring.model;

import lombok.Data;

/**
 * created by liudacheng on 2018/9/4.
 */
@Data
public class Customer extends BaseDataModel{

    private String name;
    private String customerNo;
    private String idCardIdNo;
    private String mobileNo;

}
