package com.ldc.spring.service;

import com.ldc.spring.model.Customer;

/**
 * created by liudacheng on 2018/9/4.
 */
public interface ICustomerService {

    /**
     * 展示客户信息
     */
    void displayCustomerInfo(Customer customer);

    /**
     * 校验客户是否已存在
     */
    boolean isAlreadyExist(Customer customer);

}
