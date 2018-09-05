package com.ldc.spring.core.annotation;

import com.ldc.spring.core.scene.BizScene;

import java.lang.annotation.*;

/**
 * created by liudacheng on 2018/9/5.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BeanValidator {
    Class<? extends BizScene>[] bizScenes();
}
