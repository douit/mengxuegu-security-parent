package com.mengxuegu.security.authentication.session;

import com.mengxuegu.base.result.MengxueguResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 当session失效后的处理逻辑
 */
public class CustomInvalidSessionStrategy implements InvalidSessionStrategy {
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MengxueguResult mengxueguResult = new MengxueguResult().build(HttpStatus.UNAUTHORIZED.value(), "登录超时，请重新登录");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(mengxueguResult.toJsonString());
    }
}
