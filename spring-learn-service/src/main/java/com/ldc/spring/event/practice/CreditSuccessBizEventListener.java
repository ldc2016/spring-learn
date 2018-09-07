package com.ldc.spring.event.practice;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * created by liudacheng on 2018/9/7.
 */
@Component
@AsyncListener
public class CreditSuccessBizEventListener implements ApplicationListener<CreditSuccessBizEvent> {
    private boolean isOnlineEnv;

    /**
     * Handle an application event.
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(CreditSuccessBizEvent event) {
        if (!isOnlineEnv) {
            return;
        }
//        String [] receives = ConfigUtilAdapter.getStringArray(ExceptionEvent.Thrower.LOCAL.getCode());
//
//        if (receives == null) {
//            return;
//        }
//
//        Message message = new Message();
//        message.setContent(build(event.getFundBill()));
//        message.setTo(StringUtils.join(receives, ","));
//        message.setChannel(MessageChannel.DX_EXCEPTION_ALARM.getChannel());
//
//        Senders.getSender(message).ifPresent(s -> s.sendAsync(message));
    }

//    public String build(FundBill fundBill) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("Env:").append(AppEnvUtil.getEnv()).append(",").append("发生资金兜底:").append(JSONUtil.toJSONString(fundBill));
//        return sb.toString();
//    }

    @PostConstruct
    private void init() {
//        isOnlineEnv = AppEnvUtil.getEnv() != null && "prod".equals(AppEnvUtil.getEnv());
    }
}
