package com.ldc.spring.factory;

import com.ldc.spring.model.User;

/**
 * created by liudacheng on 2018/4/12.
 */
public class UserFactory {

    public static User newInstance(){
        return new User("张三",1,"13770983345","4213509887453523");
    }
}
