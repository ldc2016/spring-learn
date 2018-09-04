package com.ldc.spring.service.impl;

import com.ldc.spring.annotation.ActionType;
import com.ldc.spring.core.enums.OperationType;
import com.ldc.spring.model.Customer;
import org.springframework.stereotype.Component;

/**
 * created by liudacheng on 2018/9/4.
 */
@Component
@ActionType(OperationType.DELETE)
public class DeleteCustomerService extends AbstractCustomerService<Customer>{

    public void deleteCustomer(Customer customer){
        System.out.println("deleteCustomer, 开始删除客户信息*******");

        doSomething(customer);

        System.out.println("deleteCustomer, 删除客户信息结束*******");
    }

    private void doSomething(Customer customer) {
        displayCustomerInfo(customer);
    }

}
