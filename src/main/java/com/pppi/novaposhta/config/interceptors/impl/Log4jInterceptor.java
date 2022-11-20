package com.pppi.novaposhta.config.interceptors.impl;

import com.pppi.novaposhta.config.interceptors.LogInterceptor;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalTime;

/**
 * LogInterceptor implementation based on Log4j logger.<br>
 * @author group2
 * @see LogInterceptor
 * @version 1.0
 * */
public class Log4jInterceptor implements LogInterceptor {

    private final Logger logger = Logger.getLogger(Log4jInterceptor.class);

    private LocalTime before;
    private LocalTime after;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        logger.info(String.format("HttpMethod: [%1$s]. Mapping: [%2$s] start.", request.getMethod(), request.getRequestURI()));
        before = LocalTime.now();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        after = LocalTime.now();
        Duration duration = Duration.between(before, after);
        logger.info(String.format("HttpMethod: [%1$s]. Mapping: [%2$s] end.", request.getMethod(), request.getRequestURI()));
        logger.info(String.format("Duration: %s nanos.", duration.toNanos()));
    }
}