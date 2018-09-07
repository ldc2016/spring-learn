package com.ldc.spring.event;

import com.ldc.spring.utils.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;

/**
 * created by liudacheng on 2018/9/7.
 */
public class EventListener implements SmartApplicationListener {
    private final static Logger LOGGER = LoggerFactory.getLogger(EventListener.class);

    /**
     * Determine whether this listener actually supports the given event type.
     * @param eventType
     */
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return BizEvent.class.isAssignableFrom(eventType);
    }

    /**
     * Determine whether this listener actually supports the given source type.
     * @param sourceType
     */
    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return true;
    }

    /**
     * Handle an application event.
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        try {
            BizEvent bizEvent = (BizEvent)event;
            Object source = bizEvent.getSource();
            //todo: 根据source来做对应的逻辑处理

        } catch (Exception e) {
            try {
                String eventInfo = JacksonUtil.toJson(event);
                LOGGER.warn("Persist event fail, event detail info :" + eventInfo, e);
            } catch (Exception ex) {
                LOGGER.warn("Persist event fail, exception is:", e);
            }
        }

    }

    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
