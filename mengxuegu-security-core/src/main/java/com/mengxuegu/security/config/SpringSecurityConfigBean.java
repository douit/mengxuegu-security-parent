package com.mengxuegu.security.config;

import com.mengxuegu.security.authentication.mobile.SmsCodeSender;
import com.mengxuegu.security.authentication.mobile.SmsSend;
import com.mengxuegu.security.authentication.session.CustomInvalidSessionStrategy;
import com.mengxuegu.security.authentication.session.CustomSessionInformationExpiredStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

/**
 * 主要为容器中添加Bena实例
 */
@Component
public class SpringSecurityConfigBean {
    /**
     * 当同一用户的session达到指定数量时，会执行该类
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new CustomSessionInformationExpiredStrategy();
    }

    /**
     * 当session失效后的处理类
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy() {
        return new CustomInvalidSessionStrategy();
    }

    /**
     * @return
     * @ConditionalOnMissingBean(SmsSend.class) 默认情况下，采用的是SmsCodeSender实例，
     * 但是如果容器当中有其他的SmsSend类型的实例，
     * 那当前的这个SmsCodeSender就失效了
     */
    @Bean
    @ConditionalOnMissingBean(SmsSend.class)
    public SmsSend smsSend() {
        return new SmsCodeSender();
    }


}
