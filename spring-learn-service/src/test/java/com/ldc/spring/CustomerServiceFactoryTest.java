package com.ldc.spring;

import com.ldc.spring.core.enums.OperationType;
import com.ldc.spring.factory.CustomerOperationServiceFactory;
import com.ldc.spring.service.ICustomerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * created by liudacheng on 2018/9/4.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = StartApplication.class) // 指定我们SpringBoot工程的Application启动类
@SpringBootApplication
public class CustomerServiceFactoryTest extends BaseSpringBootTestStarter {
    @Test
    public void TestCustomerServiceFactory(){
        ICustomerService customerService = CustomerOperationServiceFactory.getInstance().getCustomerService(OperationType.CREATE);
        log.info("customerService class name :{}", customerService.getClass().getName());
    }
}
