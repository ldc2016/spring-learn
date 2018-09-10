package com.ldc.spring.core.mybatis;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * created by liudacheng on 2018/9/7.
 */
@Intercepts(value = {
        @Signature(type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class,
                        CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class MybatisForceMasterDbInterceptor  implements Interceptor {

    private final Logger LOGGER= LoggerFactory.getLogger(MybatisForceMasterDbInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
//        String value= Tracer.getContext(MTraceConstant.paramZebraForceMasterFlag);
        String value= "true";
        LOGGER.debug("当前请求线程走主库标志：{}",value);

        if (StringUtils.isEmpty(value)){
            return invocation.proceed();
        }

        if (value.equals(String.valueOf(Boolean.TRUE))){
            // 开启强制走主库
//            ZebraForceMasterHelper.forceMasterInLocalContext();
            Object result = invocation.proceed();
            // 关闭强制走主库
//            ZebraForceMasterHelper.clearLocalContext();
            return result;
        }else{
            return invocation.proceed();
        }
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
