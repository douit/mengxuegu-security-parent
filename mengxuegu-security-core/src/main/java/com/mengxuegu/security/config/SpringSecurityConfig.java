package com.mengxuegu.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity//开启SpringSecurity过滤器链
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 加密器
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        //明文+随机盐值 然后再 加密存储
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证管理器
     * 1.认证信息(用户名，密码)
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        String password = passwordEncoder().encode("1234");
        logger.info("加密后存储的密码" + password);
        // 数据库存储的密码必须是加密后的 不然会报错：There is no PasswordEncoder mapped for the id "null"
        auth.inMemoryAuthentication().withUser("mengxuegu")
                .password(password)
                .authorities("ADMIN");
    }

    /**
     * 资源权限配置
     * 1.被拦截拦截
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic()//采用 httpBasic 认证
        http.formLogin()//表单登录方式
                .and()
                .authorizeRequests()//请求
                .anyRequest().authenticated()//所有访问该应用的http请求都有通过身份认证才可以访问
        ;
    }
}
