package com.ldc.spring.event;

import com.ldc.spring.core.enums.EventStatus;
import com.ldc.spring.core.scene.BizScene;
import org.springframework.context.ApplicationEvent;

/**
 * created by liudacheng on 2018/9/7.
 */
public abstract class BizEvent<T> extends ApplicationEvent {

    private Class<? extends BizScene> scene;
    private EventStatus status;
    private String message;
    private T source;

    /**
     * Create a new ApplicationEvent.
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public BizEvent(Class<? extends BizScene> scene, EventStatus status, String message , T source) {
        super(source);
        this.scene = scene;
        this.status = status;
        this.message = message;
    }

    @Override
    public T getSource() {
        return (T)super.getSource();
    }
}
