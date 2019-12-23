package com.mengxuegu.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * 加载认证信息的配置类
 */
@Configuration
public class ReloadMessageConfig {
    @Bean//加载中文的认证提示信息
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//        messageSource.setBasename("classpath:/org/springframework/security/messages_zh_CN");
        messageSource.setBasename("classpath:/messages_zh_CN");
        return messageSource;
    }
}
