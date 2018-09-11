package com.ldc.spring.config;

import com.google.common.collect.Maps;
import com.ldc.spring.event.practice.DistributiveEventMulticaster;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;

/**
 * created by liudacheng on 2018/9/11.
 */
@Configuration
public class CommonConfig {

    /**
     * spring重试模板类
     * @return
     */
    @Bean
    public RetryTemplate retryTemplate() {
        Map<Class<? extends Throwable>, Boolean> proxyMap = Maps.newHashMap();
        proxyMap.put(RuntimeException.class, Boolean.TRUE);
        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy(3, proxyMap);
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(simpleRetryPolicy);
        return retryTemplate;
    }

    @Bean
    public DistributiveEventMulticaster applicationEventMulticaster() {
        DistributiveEventMulticaster applicationEventMulticaster = new DistributiveEventMulticaster();
        applicationEventMulticaster.setSyncEventMulticaster(new SimpleApplicationEventMulticaster());
        SimpleApplicationEventMulticaster asyncEventMulticaster = new SimpleApplicationEventMulticaster();
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setKeepAliveSeconds(100);
        taskExecutor.setQueueCapacity(10000);
        asyncEventMulticaster.setTaskExecutor(taskExecutor);

        applicationEventMulticaster.setAsyncEventMulticaster(asyncEventMulticaster);
        return applicationEventMulticaster;
    }


}
