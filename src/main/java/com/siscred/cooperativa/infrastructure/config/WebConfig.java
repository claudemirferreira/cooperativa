package com.siscred.cooperativa.infrastructure.config;

import com.siscred.cooperativa.infrastructure.interceptor.TransactionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final TransactionInterceptor transactionInterceptor;

    @Autowired
    public WebConfig(TransactionInterceptor transactionInterceptor) {
        this.transactionInterceptor = transactionInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(transactionInterceptor);
    }
}
