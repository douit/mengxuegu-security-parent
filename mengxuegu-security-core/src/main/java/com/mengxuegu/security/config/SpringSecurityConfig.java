package com.mengxuegu.security.config;

import com.mengxuegu.security.properites.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity//开启SpringSecurity过滤器链
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SecurityProperties securityProperties;//配置文件参数

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

    @Autowired
    private UserDetailsService cutomUserDetailsService;

    /**
     * 认证管理器
     * 1.认证信息(用户名，密码)
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        String password = passwordEncoder().encode("1234");
//        logger.info("加密后存储的密码" + password);
//        // 数据库存储的密码必须是加密后的 不然会报错：There is no PasswordEncoder mapped for the id "null"
//        auth.inMemoryAuthentication().withUser("mengxuegu")
//                .password(password)
//                .authorities("ADMIN");
        //
        auth.userDetailsService(cutomUserDetailsService);
    }

    /**
     * 当你认证成功后，springsecurity它会重定向到你上一次请求
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
                .loginPage(securityProperties.getAuthentication().getLoginPage())
                .loginProcessingUrl(securityProperties.getAuthentication().getLoginProcessingUrl())//登录表单提交处理url
                .usernameParameter(securityProperties.getAuthentication().getUsernameParameter()) //对应表单中input标签中name属性的值 默认是username
                .passwordParameter(securityProperties.getAuthentication().getPasswordParameter()) //对应表单中input标签中name属性的值 默认的是password
                .and()
                .authorizeRequests()//认证请求
                .antMatchers(securityProperties.getAuthentication().getLoginPage()).permitAll() //放行/login/page请求不需要认证就可以访问
                .anyRequest().authenticated()//所有访问该应用的http请求都有通过身份认证才可以访问
        ;
    }

    /**
     * 一般针对静态资源放行
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(securityProperties.getAuthentication().getStaticPaths());
    }
}
