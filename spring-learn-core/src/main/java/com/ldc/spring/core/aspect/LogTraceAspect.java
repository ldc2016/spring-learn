package com.ldc.spring.core.aspect;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ldc.spring.core.annotation.LogTrace;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * created by liudacheng on 2018/9/5.
 */
@Aspect
@Component
@Order(2)
public class LogTraceAspect {

    private final static Logger LOGGER = LoggerFactory.getLogger(LogTraceAspect.class);

    @Around("@annotation(com.ldc.spring.core.annotation.LogTrace)")
    public Object logTrace(ProceedingJoinPoint joinPoint) throws Throwable {
        //class
        String className = joinPoint.getTarget().getClass().getName();
        //method
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();
        //annotation
        LogTrace logTrace = AnnotationUtils.getAnnotation(signature.getMethod(), LogTrace.class);
        //args
        Object[] logParams = Arrays.copyOf(joinPoint.getArgs(), joinPoint.getArgs().length);

        // 需要打印的参数
        Object[] serializeArgs = afterExcludeArgs(logParams, signature.getParameterNames(), logTrace);

        //execute
        Object result = null;
        Throwable throwable = null;

        try {
            result = joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable e) {
            throwable = e;
        }

        StringBuilder info = new StringBuilder(128);
        info.append("[").append(className).append(":").append(methodName).append("] ");
        try {
            // create gson obj
            Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                    return Arrays.stream(logTrace.excludeProperties()).anyMatch(s -> s.equals(fieldAttributes.getName()));
                }

                @Override
                public boolean shouldSkipClass(Class<?> aClass) {
                    return false;
                }
            }).create();

            //record args
            info.append("Params:").append(gson.toJson(serializeArgs)).append(",");

            if (throwable != null) {
                info.append("Exception:").append(throwable.toString());
            } else {
                info.append("Result:").append(gson.toJson(result));
            }
            LOGGER.info(info.toString());

        } catch (Exception ex) {
            LOGGER.warn(info.toString() + " logTrace io log failed.");
        } finally {
            if (throwable != null) {
                throw throwable;
            }
        }

        return result;
    }

    private Object[] afterExcludeArgs(Object[] originArgs, String[] paramNames, LogTrace logTrace) {
        List<Object> result = new ArrayList();
        String[] excludeParams = logTrace.excludeParams();

        Map<String, Boolean> mapping;
        if (excludeParams != null && excludeParams.length > 0 && paramNames != null) {
            mapping = new HashMap();
            for (String exclude : excludeParams) {
                mapping.put(exclude, true);
            }
            for (int index = 0; index < paramNames.length; index++) {
                if (!mapping.containsKey(paramNames[index])) {
                    result.add(originArgs[index]);
                }
            }
        } else {
            return originArgs;
        }

        return result.toArray();
    }


}
