package com.ldc.spring;

import com.alibaba.fastjson.JSON;
import com.ldc.spring.factory.UserFactory;
import com.ldc.spring.model.User;

/**
 * created by liudacheng on 2018/4/12.
 */
public class Test {

    public static void main(String[] args) {
        User user = UserFactory.newInstance();
        System.out.println("user info :" + JSON.toJSON(user));
        System.out.println("user info by toString : " + user.toString());
    }

}
