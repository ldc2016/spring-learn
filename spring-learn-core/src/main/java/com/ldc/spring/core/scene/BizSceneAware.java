package com.ldc.spring.core.scene;

import java.util.List;

/**
 * created by liudacheng on 2018/9/5.
 */
public interface BizSceneAware {
    List<Class<? extends BizScene>> fitFor();
}
