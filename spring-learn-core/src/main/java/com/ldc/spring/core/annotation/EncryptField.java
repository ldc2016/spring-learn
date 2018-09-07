package com.ldc.spring.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * db加密字段注解
 *
 * 用在实体上时,需要配合 {@link com.ldc.spring.core.annotation.EncryptEntity} 使用
 * 用在mapper参数名上是，需要配合 {@link org.apache.ibatis.annotations.Param} 指定参数名使用
 *
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptField {
}
