package com.ldc.spring.Utils;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * created by liudacheng on 2018/9/4.
 */
public class ObjectCloneUtil {

    public static Object copyObject(Object source) throws Exception{
        Object target=new Object();
        PropertyUtils.copyProperties(source, target);
        return target;
    }
}
