package com.ldc.spring.core.mybatis;

import com.alibaba.fastjson.JSON;
import com.ldc.spring.core.annotation.EncryptEntity;
import com.ldc.spring.core.annotation.EncryptField;
import com.ldc.spring.core.util.EncodeDecodeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * db加密/解密拦截器
 * created by liudacheng on 2018/9/7.
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class MyBatisEncryptInterceptor implements Interceptor {

    private final Logger LOGGER = LoggerFactory.getLogger(MyBatisEncryptInterceptor.class);

    /**
     * 加密字段后缀
     */
    private static final String ENCRYPT_FIELD_SUFFIX = "encrypt";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement statement = (MappedStatement) invocation.getArgs()[0];

        SqlCommandType sqlCommandType = statement.getSqlCommandType();
        if (SqlCommandType.SELECT == sqlCommandType) {
            // 对加密字段参数进行解密
            return decryptField(invocation.proceed());

        } else if (SqlCommandType.INSERT == sqlCommandType || SqlCommandType.UPDATE == sqlCommandType) {
            Object param = invocation.getArgs()[1];
            // 对加密字段参数进行加密
            encryptField(statement, param);
            return invocation.proceed();
        } else {
            return invocation.proceed();
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * db字段加密
     * @param paramObject
     * @return
     */
    private void encryptField(MappedStatement statement, Object paramObject) throws Throwable {
        if (paramObject == null) {
            return;
        }

        if (paramObject instanceof Map) {
            Map paramMap = (HashMap) paramObject;
            String mapperMethodName = statement.getId();
            Class clazz = Class.forName(mapperMethodName.substring(0, mapperMethodName.lastIndexOf(".")));
            String methodName = mapperMethodName.substring(mapperMethodName.lastIndexOf(".") + 1);
            Method[] methods = clazz.getMethods();

            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Parameter[] parameters = method.getParameters();
                    for (Parameter parameter : parameters) {
                        EncryptField annotation = parameter.getAnnotation(EncryptField.class);
                        if (annotation != null) {
                            LOGGER.info("开始对参数进行加密,call encryptField, paramObject={}", JSON.toJSONString(paramObject));

                            Param paramAnnotation = parameter.getAnnotation(Param.class);
                            if (paramAnnotation == null) {
                                LOGGER.error("参数使用@EncryptField加密，但未使用@Param注解指定参数名, 处理出错. Mapper={}", mapperMethodName);
                                throw new RuntimeException("加密字段未使用@Param注解指定参数名, 处理出错. Mapper=" + mapperMethodName);
                            }
                            String rawFieldKey = paramAnnotation.value();
                            String rawFieldValue = (String) paramMap.get(rawFieldKey);

                            // 空值不做加密处理
                            if (StringUtils.isNotBlank(rawFieldValue)) {
                                paramMap.put(rawFieldKey, EncodeDecodeUtil.encryptWithAesAlgorithm(rawFieldValue));
                            }

                            LOGGER.info("完成对参数进行加密,call encryptField, paramObject={}", JSON.toJSONString(paramObject));
                        }

                    }
                }
            }

            //todo 切换处理逻辑，后续删除
            processMapParamWriteSwitch(paramMap);

        } else {
            Field[] declaredFields = paramObject.getClass().getDeclaredFields();
            if (ArrayUtils.isNotEmpty(declaredFields)) {
                for (int i = 0; i < declaredFields.length; i++) {
                    Field field = declaredFields[i];
                    field.setAccessible(true);

                    if (field.isAnnotationPresent(EncryptField.class) && field.getType() == String.class) {
                        LOGGER.info("开始对参数进行加密,call encryptField, paramObject={}", JSON.toJSONString(paramObject));

                        Object rawValue = field.get(paramObject);

                        // 空串不做处理
                        if (rawValue != null && StringUtils.isNotBlank(String.valueOf(rawValue))) {
                            String encryptedValue = EncodeDecodeUtil.encryptWithAesAlgorithm(String.valueOf(rawValue));

                            field.set(paramObject, encryptedValue);
                        }

                        // todo 切换处理逻辑，后续删除
                        processWriteSwitch(paramObject, declaredFields, field);

                        LOGGER.info("完成对参数进行加密,call encryptField, paramObject={}", JSON.toJSONString(paramObject));
                    }
                }
            }
        }
    }

    /**
     * db字段解密
     *
     * @param resultValue
     * @return
     */
    private Object decryptField(Object resultValue) throws Throwable {
        if (resultValue == null) {
            return null;
        }

        if (resultValue instanceof List && CollectionUtils.isNotEmpty((List) resultValue)) {
            EncryptEntity annotation = ((List) resultValue).get(0).getClass().getAnnotation(EncryptEntity.class);
            if (annotation == null) {
                return resultValue;
            }

            LOGGER.info("开始对结果集进行解密,call decryptField, paramObject={}", JSON.toJSONString(resultValue));

            for (Object obj : (List) resultValue) {
                Field[] declaredFields = obj.getClass().getDeclaredFields();
                if (ArrayUtils.isNotEmpty(declaredFields)) {
                    for(int i = 0; i < declaredFields.length; i++) {
                        Field field = declaredFields[i];
                        field.setAccessible(true);

                        if (field.isAnnotationPresent(EncryptField.class) && field.getType() == String.class) {
                            Object encryptedValue = field.get(obj);

                            // 空串不做处理
                            if (encryptedValue != null && StringUtils.isNotBlank(String.valueOf(encryptedValue))) {
                                String decryptedValue = EncodeDecodeUtil.decryptWithAesAlgorithm(String.valueOf(encryptedValue));
                                field.set(obj, decryptedValue);

                                // todo 切换处理逻辑，后续删除
                                processReadSwitch(obj, declaredFields, field, decryptedValue);
                            }
                        }
                    }
                }
            }

            LOGGER.info("完成对结果集进行解密,call decryptField, paramObject={}", JSON.toJSONString(resultValue));
        }

        return resultValue;
    }

    /**
     * 切换开关开: 加密字段解密后赋值给原始字段
     * 切换开关关: 不做处理
     *
     * @param obj
     * @param declaredFields
     * @param encryptedField
     * @param decryptedValue
     * @throws Throwable
     */
    private void processReadSwitch(Object obj, Field[] declaredFields,
                                   Field encryptedField, String decryptedValue) throws Throwable {
        if (true) {
            LOGGER.info("开始processReadSwitch, paramObject={}", JSON.toJSONString(obj));

            for(int i = 0; i < declaredFields.length; i++) {
                Field field = declaredFields[i];
                // 如果是加密字段对应的原始字段，则赋值为加密字段解密后值
                if (field.getName().equals(encryptedField.getName().replace(ENCRYPT_FIELD_SUFFIX, ""))) {
                    field.setAccessible(true);
                    field.set(obj, decryptedValue);
                }
            }

            LOGGER.info("完成processReadSwitch, paramObject={}", JSON.toJSONString(obj));
        }
    }

    /**
     * 处理对象参数
     *
     * 切换开关开: 原始字段写明文, 加密字段写原始字段加密后的密文
     * 切换开关关: 原始字段写明文, 加密字段写原始字段加密后的密文
     *
     * @param obj
     * @param declaredFields
     * @param encryptedField
     * @throws Throwable
     */
    private void processWriteSwitch(Object obj, Field[] declaredFields,
                                    Field encryptedField) throws Throwable {
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];

            // 如果是加密字段对应的原始字段
            if (field.getName().equals(encryptedField.getName().replace(ENCRYPT_FIELD_SUFFIX, ""))) {
                LOGGER.info("开始processWriteSwitch, paramObject={}", JSON.toJSONString(obj));

                // 将原始字段明文加密后赋值给加密字段
                Object rawValue = field.get(obj);
                // 空串不做处理
                if (rawValue != null && StringUtils.isNotBlank(String.valueOf(rawValue))) {
                    String encryptedValue = EncodeDecodeUtil.encryptWithAesAlgorithm(String.valueOf(rawValue));

                    encryptedField.set(obj, encryptedValue);
                }

                LOGGER.info("完成processWriteSwitch, paramObject={}", JSON.toJSONString(obj));
            }
        }
    }

    /**
     * 处理map参数
     *
     * 切换开关开: 原始字段写明文, 加密字段写原始字段加密后的密文
     * 切换开关关: 原始字段写明文, 加密字段写原始字段加密后的密文
     *
     * @param paramMap
     */
    private void processMapParamWriteSwitch(Map paramMap) {
        paramMap.forEach((k, v) -> {

            // 如果是加密字段
            if (k instanceof String && ((String) k).endsWith(ENCRYPT_FIELD_SUFFIX)) {
                LOGGER.info("开始processMapParamWriteSwitch, paramObject={}", JSON.toJSONString(paramMap));

                // 对应的原始字段
                String rawFieldKey = ((String) k).replace(ENCRYPT_FIELD_SUFFIX, "");

                if (paramMap.containsKey(rawFieldKey)) {
                    String rawFieldValue = (String) paramMap.get(rawFieldKey);

                    if (StringUtils.isNotBlank(rawFieldValue)) {
                        paramMap.put(k, EncodeDecodeUtil.encryptWithAesAlgorithm(rawFieldValue));
                    }
                }

                LOGGER.info("完成processMapParamWriteSwitch, paramObject={}", JSON.toJSONString(paramMap));
            }
        });
    }
}
