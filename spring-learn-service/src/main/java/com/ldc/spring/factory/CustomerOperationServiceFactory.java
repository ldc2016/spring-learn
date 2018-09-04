package com.ldc.spring.factory;

import com.ldc.spring.core.enums.OperationType;
import com.ldc.spring.service.ICustomerService;
import com.ldc.spring.register.CustomerOperationServiceRegister;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * created by liudacheng on 2018/9/4.
 */
@Component
public class CustomerOperationServiceFactory implements InitializingBean{

    private static CustomerOperationServiceFactory instance;
    private static Map<OperationType, ICustomerService> builders;

    @Autowired
    public CustomerOperationServiceFactory(CustomerOperationServiceRegister customerOperationServiceRegister,
                                           ICustomerService[] customerServices) {
        builders = customerOperationServiceRegister.build(customerServices);
    }

    public static CustomerOperationServiceFactory getInstance(){
        return instance;
    }

    public ICustomerService getCustomerService(OperationType operationType) {
        ICustomerService customerService = builders.get(operationType);
        if (customerService == null) {
            throw new RuntimeException("操作类型找不到对应的ICustomerService, operationType = " + operationType);
        }

        return customerService;
    }

    private static void setInstance(CustomerOperationServiceFactory instance) {
        CustomerOperationServiceFactory.instance = instance;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setInstance(this);
    }
}
