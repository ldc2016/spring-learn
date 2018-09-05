package com.ldc.spring.service.impl;

import com.ldc.spring.annotation.ActionType;
import com.ldc.spring.core.annotation.BeanValidator;
import com.ldc.spring.core.annotation.LogTrace;
import com.ldc.spring.core.enums.OperationType;
import com.ldc.spring.model.Customer;
import com.ldc.spring.scene.DefaultScene;
import org.springframework.stereotype.Component;

/**
 * created by liudacheng on 2018/9/4.
 */
@Component
@ActionType(OperationType.CREATE)
public class AddCustomerService extends AbstractCustomerService<Customer>{

    @LogTrace
    @BeanValidator(bizScenes = {DefaultScene.class})
    public void addCustomer(Customer customer){
        System.out.println("addCustomer, 开始插入客户信息*******");

        doSomething(customer);

        System.out.println("addCustomer, 插入客户信息结束*******");
    }

    private void doSomething(Customer customer) {
        displayCustomerInfo(customer);
    }

}
