package com.ldc.spring.exceptionhandler;

import com.alibaba.fastjson.JSON;
import com.ldc.spring.core.model.ExceptionConfig;
import com.ldc.spring.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * created by liudacheng on 2018/9/10.
 */
@Component
public class ExceptionResolver implements HandlerExceptionResolver, InitializingBean, DisposableBean {
    private Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);

    @Autowired
    private ExceptionConfigMsgContainer exceptionConfigMsgContainer;

    private static final int SYS_ERROR_CODE = 500;
    private static final String SYS_ERROR_MSG = "服务器异常，请后退重试";

    /**
     * 在这里处理所有得异常信息
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse resp, Object o, Exception ex) {
        // 全部异常信息
        Map<String, ExceptionConfig> exceptionMessageMap =
                exceptionConfigMsgContainer.getExceptionConfigMap();

        for (ExceptionConfig exceptionMessage : exceptionMessageMap.values()) {
            if (ex.getClass().getName().equals(exceptionMessage.getExceptionClass())) {
                // 0为error，1为warn，2为info
                switch (exceptionMessage.getLogLevel()) {
                    case 0:
                        logger.error(ex.getMessage(), exceptionMessage.isPrintExceptionStack() ? ex : null);
                        break;
                    case 1:
                        logger.warn(ex.getMessage(), exceptionMessage.isPrintExceptionStack() ? ex : null);
                        break;
                    case 2:
                        logger.info(ex.getMessage(), exceptionMessage.isPrintExceptionStack() ? ex : null);
                        break;
                    default:
                        break;
                }

                writeFrontMsg(exceptionMessage.getErrorCode(), exceptionMessage.getExceptionMessage(), resp);
                return new ModelAndView();
            }
        }

        logger.warn(ex.getMessage(), ex);
        writeFrontMsg(SYS_ERROR_CODE, SYS_ERROR_MSG, resp);
        return new ModelAndView();
    }

    /**
     * 将错误信息添加到response中
     *
     * @param msg
     * @param response
     */
    public void writeFrontMsg(int errorCode, String msg, HttpServletResponse response) {
        PrintWriter pw = null;
        try {
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            pw = response.getWriter();
            Response resultResponse = Response.fail(msg,String.valueOf(errorCode));
            pw.write(JSON.toJSONString(resultResponse));
            pw.flush();
        } catch (Exception e) {
            logger.warn("write to response error", e);
        }finally {
            if(pw != null){
                pw.close();
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        exceptionConfigMsgContainer.start();
    }

    @Override
    public void destroy() throws Exception {
        exceptionConfigMsgContainer.shutdown();
    }
}
