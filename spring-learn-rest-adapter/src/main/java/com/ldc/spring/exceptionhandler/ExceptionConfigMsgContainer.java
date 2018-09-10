package com.ldc.spring.exceptionhandler;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.ldc.spring.core.model.ExceptionConfig;
import com.ldc.spring.repository.ExceptionConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 异常信息容器
 */
@Component
public class ExceptionConfigMsgContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionConfigMsgContainer.class);

    /**
     * 定时任务线程池
     */
    private ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(
            1, new ThreadFactoryBuilder().setNameFormat("fetch-exceptionConfig-pool-%d").build());

    /**
     * 线程池启动标记
     */
    private volatile boolean started = false;

    /**
     * 存放全部异常信息
     */
    private ConcurrentHashMap<String, ExceptionConfig> exceptionConfigMap = new ConcurrentHashMap<>();

    /**
     * 定时任务周期:10 min
     * 每10 min取一次全部异常信息
     */
    private final int SCHEDULE_PERIOD = 10 * 60 * 1000;

    @Autowired
    private ExceptionConfigRepository exceptionConfigRepository;


    private class RefreshMessageMapTask implements Runnable {
        @Override
        public void run() {
            refreshExceptionMessageMap();
        }
    }

    public void start() {
        // 开启定时任务
        scheduledExecutorService.scheduleAtFixedRate(new RefreshMessageMapTask(), 0, SCHEDULE_PERIOD, TimeUnit.MILLISECONDS);
        refreshExceptionMessageMap();
        addShutDownHook();
        started = true;
    }

    public void shutdown() {
        scheduledExecutorService.shutdownNow();
        started = false;
    }

    public ExceptionConfig getExceptionConfigByClass(Class<?> cls) {
        if (!started) {
            start();
        }
        return exceptionConfigMap.get(cls.getName());
    }

    public Map<String, ExceptionConfig> getExceptionConfigMap() {
        if (!started) {
            start();
        }
        return exceptionConfigMap;
    }

    private void refreshExceptionMessageMap() {
        List<ExceptionConfig> exceptionMessagePOList=exceptionConfigRepository.queryAllExceptionConfigs();
        LOGGER.info("获取全部异常信息，条数：{}",exceptionMessagePOList.size());
        for (ExceptionConfig exceptionConfig : exceptionMessagePOList) {
            exceptionConfigMap.put(exceptionConfig.getExceptionClass(), exceptionConfig);
        }

    }

    private void addShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                scheduledExecutorService.shutdownNow();
            }
        }));
    }


}
