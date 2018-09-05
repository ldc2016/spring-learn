package com.ldc.spring.service.impl;

import com.ldc.spring.annotation.ActionType;
import com.ldc.spring.core.enums.OperationType;
import com.ldc.spring.model.Customer;
import org.springframework.stereotype.Component;

/**
 * created by liudacheng on 2018/9/4.
 */
@Component
@ActionType(OperationType.READ)
public class QueryCustomerService extends AbstractCustomerService<Customer>{

    public void getCustomer(Customer customer){
        System.out.println("getCustomer, 开始查询客户信息*******");

        doSomething(customer);

        System.out.println("getCustomer, 查询客户信息结束*******");
    }

    private void doSomething(Customer customer) {
        displayCustomerInfo(customer);
    }
}
