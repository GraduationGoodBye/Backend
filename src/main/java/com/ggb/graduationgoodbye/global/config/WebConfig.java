package com.ggb.graduationgoodbye.global.config;

import com.ggb.graduationgoodbye.global.config.log.LogFilter;
import jakarta.servlet.*;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<Filter>();
        filterRegistrationBean.setFilter(new LogFilter()); // 필터 등록
        filterRegistrationBean.setOrder(1); // 필터 순서
        filterRegistrationBean.addUrlPatterns("/*"); // 적용 URL 설정
        return filterRegistrationBean;
    }

}