package com.ldc.spring.utils;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.base.Predicates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.TimeUnit;

/**
 * created by liudacheng on 2018/9/12.
 */
public class RetryerUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(RetryerUtil.class);
    // 重试时sleep时间
    private static final int SLEEP_TIME_MILLISECONDS = 10;
    // 重试次数
    private static final int ATTEMPT_NUMBER = 2;

    private static Retryer<Boolean> retryer;

    static {
        retryer = RetryerBuilder.<Boolean>newBuilder()
                //抛出runtime异常、checked异常时都会重试，但是抛出error不会重试。
                .retryIfException()
                //返回false也需要重试
                .retryIfResult(Predicates.equalTo(false))
                //重调策略,间隔1毫秒
                .withWaitStrategy(WaitStrategies.fixedWait(SLEEP_TIME_MILLISECONDS, TimeUnit.MILLISECONDS))
                //尝试次数
                .withStopStrategy(StopStrategies.stopAfterAttempt(ATTEMPT_NUMBER))
                .build();
    }


    public static Retryer<Boolean> getRetryer() {
        return retryer;
    }
}
