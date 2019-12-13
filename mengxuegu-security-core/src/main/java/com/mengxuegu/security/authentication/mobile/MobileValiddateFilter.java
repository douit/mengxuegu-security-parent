package com.mengxuegu.security.authentication.mobile;

import com.mengxuegu.security.authentication.CustomAuthenticationFailureHandler;
import com.mengxuegu.security.controller.MobileLoginController;
import com.mengxuegu.security.exception.ValidateCodeException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 校验用户输入的手机验证码是否正确
 */
@Component
public class MobileValiddateFilter extends OncePerRequestFilter {
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1.判断请求是否为手机登录，且post请求
        if ("/mobile/form".equals(request.getRequestURI()) && "post".equalsIgnoreCase(request.getMethod())) {
            try {
                //校验验证码合法性
                validate(request);
            } catch (AuthenticationException e) {
                //交给失败处理器处理异常
                customAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        //放行
        filterChain.doFilter(request, response);
    }

    private void validate(HttpServletRequest request) {
        //现获取session中的验证码
        String sessionCode = (String) request.getSession().getAttribute(MobileLoginController.SESSION_KEY);
        //获取用户输入的验证码
        String inputCode = request.getParameter("code");
        //判断是否正确
        if (StringUtils.isBlank(inputCode)) {
            throw new ValidateCodeException("验证码不能为空");
        }
        if (!inputCode.equalsIgnoreCase(sessionCode)) {
            throw new ValidateCodeException("验证码输入错误");
        }
    }
}
