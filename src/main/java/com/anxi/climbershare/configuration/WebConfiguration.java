package com.anxi.climbershare.configuration;

import com.anxi.climbershare.interceptor.NeedLoginInterceptor;
import com.anxi.climbershare.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Component
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    PassportInterceptor passportInterceptor;

    @Autowired
    NeedLoginInterceptor needLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(needLoginInterceptor).addPathPatterns("/user/*");
        super.addInterceptors(registry);
    }
}
