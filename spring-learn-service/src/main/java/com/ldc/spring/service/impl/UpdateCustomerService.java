package com.ldc.spring.service.impl;

import com.ldc.spring.annotation.ActionType;
import com.ldc.spring.core.enums.OperationType;
import com.ldc.spring.model.Customer;
import org.springframework.stereotype.Component;

/**
 * created by liudacheng on 2018/9/4.
 */
@Component
@ActionType(OperationType.UPDATE)
public class UpdateCustomerService extends AbstractCustomerService<Customer>{

    public void updateCustomer(Customer customer){
        System.out.println("updateCustomer, 开始更新客户信息*******");

        doSomething(customer);

        System.out.println("updateCustomer, 更新客户信息结束*******");
    }

    private void doSomething(Customer customer) {
        displayCustomerInfo(customer);
    }

}
