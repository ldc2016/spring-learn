package com.ldc.spring;

import com.alibaba.fastjson.JSON;
import com.ldc.spring.core.enums.ProductType;
import com.ldc.spring.core.enums.RepayType;
import com.ldc.spring.core.model.User;
import com.ldc.spring.mapper.UserMapper;
import com.ldc.spring.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * created by liudacheng on 2018/9/6.
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = StartApplication.class) // 指定我们SpringBoot工程的Application启动类
@SpringBootApplication
public class UserMapperTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Test
    public void TestCustomerServiceFactory(){
        User user = new User("张三",1,"13770546607","43212323432223323");
        user.setRepayType(RepayType.CASHIER);
        user.setProductType(ProductType.CASH_LOAN);
        user.setId(10002L);
        long id = userRepository.addUser(user);
        log.info("id : {}",id );
    }

    @Test
    public void testGetById(){
        User user = userMapper.getById(10002L);
        log.info("user ：" + JSON.toJSON(user));
    }
}
