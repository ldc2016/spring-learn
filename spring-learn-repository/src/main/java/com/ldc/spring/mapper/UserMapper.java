package com.ldc.spring.mapper;

import com.ldc.spring.core.model.User;
import org.apache.ibatis.annotations.Param;

/**
 * created by liudacheng on 2018/9/6.
 */
public interface UserMapper {
    long insertSelective(User user);

    User getById(@Param("id") long id);
}
