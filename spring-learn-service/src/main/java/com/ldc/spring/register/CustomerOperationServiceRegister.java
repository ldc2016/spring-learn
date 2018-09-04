package com.ldc.spring.register;

import com.google.common.base.Preconditions;
import com.ldc.spring.annotation.ActionType;
import com.ldc.spring.core.enums.OperationType;
import org.springframework.stereotype.Component;

/**
 * created by liudacheng on 2018/9/4.
 */
@Component
public class CustomerOperationServiceRegister extends AbstractEnumRegister<OperationType> {

    @Override
    public OperationType[] getKeys(Class<?> targetClass) {
        ActionType actionType = targetClass.getAnnotation(ActionType.class);
        Preconditions.checkNotNull(actionType, "Cannot find @OperationType annotation on %s",
                targetClass.getName());

        return actionType.value();
    }
}
