package com.api.wiveService.WineService.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("##############       INICIO endpoint: " + request.getRequestURI());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // No-op
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (ex != null) {
            StackTraceElement[] stackTrace = ex.getStackTrace();
            if (stackTrace.length > 0) {
                StackTraceElement element = stackTrace[0];
                logger.error("##############       Erro no endpoint: {}. Exceção: {}. Método: {}. Linha: {}",
                        request.getRequestURI(),
                        ex.getMessage(),
                        element.getMethodName(),
                        element.getLineNumber(),
                        ex);
            } else {
                logger.error("##############       Erro no endpoint: {}. Exceção: {}",
                        request.getRequestURI(),
                        ex.getMessage(),
                        ex);
            }
        } else {
            logger.info("##############       FIM endpoint: " + request.getRequestURI());
        }
    }
}
