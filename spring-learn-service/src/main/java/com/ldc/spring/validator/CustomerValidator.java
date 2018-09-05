package com.ldc.spring.validator;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.ldc.spring.core.exception.BeanValidateException;
import com.ldc.spring.core.scene.BizScene;
import com.ldc.spring.core.validator.IBeanValidator;
import com.ldc.spring.model.Customer;
import com.ldc.spring.scene.DefaultScene;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * created by liudacheng on 2018/9/5.
 */
@Component
public class CustomerValidator implements IBeanValidator<Customer>{

    @Override
    public void validate(Customer customer) throws BeanValidateException {
        if(customer == null || StringUtils.isEmpty(customer.getCustomerNo())){
            throw new BeanValidateException("客户号为空！【customer】: " + JSON.toJSON(customer));
        }
    }

    @Override
    public List<Class<? extends BizScene>> fitFor() {
        return Lists.newArrayList(DefaultScene.class);
    }
}
