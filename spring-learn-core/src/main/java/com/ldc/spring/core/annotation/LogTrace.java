package com.ldc.spring.core.annotation;

import java.lang.annotation.*;

/**
 * created by liudacheng on 2018/9/5.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogTrace {

    String[] excludeParams() default {};

    String[] excludeProperties() default {};
}
