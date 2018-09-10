package com.ldc.spring.reslover;

import com.ldc.spring.core.model.BizSerialNo;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


public class SerialNoResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> parameterType = parameter.getParameterType();
        return parameterType.isAssignableFrom(BizSerialNo.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Class<?> parameterType = parameter.getParameterType();
        if (parameterType.isAssignableFrom(BizSerialNo.class)) {
            String bizSerialNumber = webRequest.getParameter("bizSerialNumber");
            if (bizSerialNumber != null) {
                return new BizSerialNo(bizSerialNumber);
            }
        }

        return null;
    }
}
