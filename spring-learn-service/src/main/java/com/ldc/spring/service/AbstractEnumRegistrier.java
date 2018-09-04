package com.ldc.spring.service;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * created by liudacheng on 2018/9/4.
 */
public abstract class AbstractEnumRegistrier<K extends Enum> {

    public <V> Map<K, V> build(V[] targetBeans) {
        ImmutableMap.Builder<K, V> mapBuilder = ImmutableMap.builder();
        for (V bean : targetBeans) {
            Class<?> builderClass = bean.getClass();
            K[] keys = getKeys(builderClass);
            for (K key : keys) {
                mapBuilder.put(key, bean);
            }
        }
        return mapBuilder.build();
    }

    public abstract K[] getKeys(Class<?> targetClass);
}
