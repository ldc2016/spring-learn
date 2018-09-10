package com.ldc.spring.repository;

import com.ldc.spring.core.model.ExceptionConfig;

import java.util.List;

/**
 * created by liudacheng on 2018/9/10.
 */
public interface ExceptionConfigRepository {

    List<ExceptionConfig> queryAllExceptionConfigs();
}
