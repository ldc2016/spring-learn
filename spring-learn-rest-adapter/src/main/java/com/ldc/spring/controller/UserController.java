package com.ldc.spring.controller;

import com.ldc.spring.core.annotation.LogTrace;
import com.ldc.spring.core.model.User;
import com.ldc.spring.repository.UserRepository;
import com.ldc.spring.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by liudacheng on 2018/9/10.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * 查询用户积分权益
     */
    @RequestMapping("/queryUserById")
    @LogTrace
    public Response<User> queryUserById(long id) {
        User user = userRepository.getUser(10002L);
        return Response.success(user);
    }
}
