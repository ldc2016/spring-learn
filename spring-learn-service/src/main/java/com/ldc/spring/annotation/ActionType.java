package com.ldc.spring.annotation;

import com.ldc.spring.core.enums.OperationType;

import java.lang.annotation.*;

/**
 * created by liudacheng on 2018/9/4.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface ActionType {

    OperationType[] value();
}
