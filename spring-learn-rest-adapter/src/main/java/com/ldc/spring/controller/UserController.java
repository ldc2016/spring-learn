package com.ldc.spring.controller;

import com.alibaba.fastjson.JSON;
import com.ldc.spring.core.annotation.LogTrace;
import com.ldc.spring.core.model.User;
import com.ldc.spring.repository.UserRepository;
import com.ldc.spring.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
    public Map<String, Object> queryUserById(long id) {
        User user = userRepository.getUser(10002L);
        return ResultUtil.success(JSON.toJSONString(user));
    }
}
