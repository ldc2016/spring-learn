package com.ldc.spring.core.validator;

import com.ldc.spring.core.exception.BeanValidateException;
import com.ldc.spring.core.scene.BizSceneAware;

/**
 * created by liudacheng on 2018/9/5.
 */
public interface IBeanValidator<T extends Validatable> extends BizSceneAware {

    void validate(T t) throws BeanValidateException;
}
