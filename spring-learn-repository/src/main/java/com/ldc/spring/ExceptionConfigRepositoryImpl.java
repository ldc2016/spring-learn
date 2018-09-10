package com.ldc.spring;

import com.google.common.collect.Lists;
import com.ldc.spring.core.model.ExceptionConfig;
import com.ldc.spring.repository.ExceptionConfigRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * created by liudacheng on 2018/9/10.
 */
@Repository
public class ExceptionConfigRepositoryImpl implements ExceptionConfigRepository{

    @Override
    public List<ExceptionConfig> queryAllExceptionConfigs() {
        return Lists.newArrayList();
    }
}
