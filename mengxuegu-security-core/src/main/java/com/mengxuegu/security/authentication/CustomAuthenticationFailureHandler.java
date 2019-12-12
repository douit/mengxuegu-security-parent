package com.mengxuegu.security.authentication;

import com.mengxuegu.base.result.MengxueguResult;
import com.mengxuegu.security.properites.LoginResponseType;
import com.mengxuegu.security.properites.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义认证失败处理器
 */
@Component("customAuthenticationFailureHandler")
//public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Autowired
    SecurityProperties securityProperties;

    /**
     * @param e 认证失败时候的异常
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        if (LoginResponseType.JSON.equals(securityProperties.getAuthentication().getLoginType())) {
            //认证失败响应json字符串
            MengxueguResult result = MengxueguResult.build(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            String jsonString = result.toJsonString();
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(jsonString);
        } else {
            //设置失败时候默认访问的地址
            super.setDefaultFailureUrl(securityProperties.getAuthentication().getLoginPage() + "?error");
            //重定向回认证页面
            super.onAuthenticationFailure(request, response, e);
        }
    }
}
