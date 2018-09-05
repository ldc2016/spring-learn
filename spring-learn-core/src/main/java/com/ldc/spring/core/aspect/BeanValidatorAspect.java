package com.ldc.spring.core.aspect;

import com.ldc.spring.core.annotation.BeanValidator;
import com.ldc.spring.core.scene.BizScene;
import com.ldc.spring.core.validator.IBeanValidator;
import com.ldc.spring.core.validator.Validatable;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * created by liudacheng on 2018/9/5.
 */
@Aspect
@Component
public class BeanValidatorAspect implements ApplicationContextAware {

    private static ApplicationContext context;

    private static final Map<Class<? extends BizScene>, Map<Class, List<IBeanValidator>>> VALIDATOR_CACHE = new ConcurrentHashMap();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    @Before("@annotation(com.ldc.spring.core.annotation.BeanValidator)")
    public void businessSceneBeanValidate(JoinPoint joinPoint) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        Object[] args = joinPoint.getArgs();
        Class[] argTypes = methodSignature.getParameterTypes();

        BeanValidator beanValidator = AnnotationUtils.getAnnotation(method, BeanValidator.class);

        Class<? extends BizScene>[] scenes = beanValidator.bizScenes();

        for (Class<? extends BizScene> scene : scenes) {
            //get bean validator mapping by scene from cache
            Map<Class, List<IBeanValidator>> beanValidatorMapping = VALIDATOR_CACHE.get(scene);
            if (beanValidatorMapping == null) {
                beanValidatorMapping = getBeanValidatorMappingByScene(scene);
                VALIDATOR_CACHE.put(scene, beanValidatorMapping);
            }
            //bean validator
            List<IBeanValidator> modelValidators;
            if (argTypes != null && argTypes.length > 0) {
                for (int index = 0; index < argTypes.length; index++) {
                    modelValidators = beanValidatorMapping.get(argTypes[index]);
                    if (modelValidators != null) {
                        for (IBeanValidator modelValidator : modelValidators) {
                            modelValidator.validate((Validatable) args[index]);
                        }
                    }
                }
            }
        }


    }


    private Map<Class, List<IBeanValidator>> getBeanValidatorMappingByScene(Class<? extends BizScene> scene) {

        Map<Class, List<IBeanValidator>> mapping = new HashMap(4);
        Map<String, IBeanValidator> beans = context.getBeansOfType(IBeanValidator.class);

        if (beans != null && !beans.isEmpty()) {
            Class beanClz;
            for (IBeanValidator beanValidator : beans.values()) {
                beanClz = validatorModelType(beanValidator);
                List<Class<? extends BizScene>> fitScenes = beanValidator.fitFor();

                if (fitScenes != null && fitScenes.contains(scene) && beanClz != null) {
                    if (mapping.containsKey(beanClz)) {
                        mapping.get(beanClz).add(beanValidator);
                    } else {
                        List<IBeanValidator> value = new ArrayList();
                        value.add(beanValidator);
                        mapping.put(beanClz, value);
                    }
                }
            }
        }
        return mapping;
    }


    private Class validatorModelType(IBeanValidator modelValidator) {
        Type[] types = modelValidator.getClass().getGenericInterfaces();
        for (Type type : types) {
            if (ParameterizedType.class.isAssignableFrom(type.getClass())) {
                if (((ParameterizedType) type).getRawType().equals(IBeanValidator.class)) {
                    return (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
                }
            }
        }
        //should not happen
        return null;
    }
}
