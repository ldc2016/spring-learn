package com.ldc.spring;

import com.ldc.spring.core.enums.OperationType;
import com.ldc.spring.factory.CustomerOperationServiceFactory;
import com.ldc.spring.model.Customer;
import com.ldc.spring.service.impl.AddCustomerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * created by liudacheng on 2018/9/5.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = StartApplication.class) // 指定我们SpringBoot工程的Application启动类
@SpringBootApplication
public class LogTraceAnnotationTest {

    @Test
    public void TestAddCustomer(){
        AddCustomerService addCustomerService = (AddCustomerService) CustomerOperationServiceFactory.getInstance().getCustomerService(OperationType.CREATE);
        log.info("customerService class name :{}", addCustomerService.getClass().getName());
        Customer customer = new Customer();
        customer.setCustomerNo("");
        customer.setIdCardIdNo("421381199007011331");
        customer.setMobileNo("13770526605");
        customer.setName("张丹凤");

        addCustomerService.addCustomer(customer);
    }
}
