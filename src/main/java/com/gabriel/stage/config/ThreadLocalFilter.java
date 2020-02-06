package com.gabriel.stage.config;


import cn.hutool.core.lang.UUID;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author: Gabriel
 * @date: 2020/1/28 21:14
 * @description 过滤器测试
 */
@Slf4j
@Component
@WebFilter(urlPatterns = "/**",filterName = "tlFilter")
public class ThreadLocalFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
      log.info("过滤器初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String logtrackId = UUID.randomUUID(true).toString();
        MDC.put("logTrackId",logtrackId);
        log.info("过滤器执行中");
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        }finally{
            MDC.remove(logtrackId);
        }
        log.info("过滤器执行完成");
    }

    @Override
    public void destroy() {
        log.info("过滤器销毁");

    }
}
