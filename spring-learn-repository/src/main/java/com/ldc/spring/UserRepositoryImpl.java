package com.ldc.spring;

import com.ldc.spring.core.model.User;
import com.ldc.spring.mapper.UserMapper;
import com.ldc.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * created by liudacheng on 2018/9/6.
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private UserMapper userMapper;

    @Override
    public long addUser(User user) {
        return userMapper.insertSelective(user);
    }
}
