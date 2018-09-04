package com.ldc.spring.service.impl;

import com.alibaba.fastjson.JSON;
import com.ldc.spring.model.BaseDataModel;
import com.ldc.spring.model.Customer;
import com.ldc.spring.service.ICustomerService;

/**
 * created by liudacheng on 2018/9/4.
 */
public abstract class AbstractCustomerService <T extends BaseDataModel> implements ICustomerService{

    /**
     * 展示客户信息
     * @param customer
     */
    @Override
    public void displayCustomerInfo(Customer customer) {
        System.out.println("customer info is : " + JSON.toJSON(customer));
    }

    /**
     * 校验客户是否已存在
     * @param customer
     */
    @Override
    public boolean isAlreadyExist(Customer customer) {
        return false;
    }

}
