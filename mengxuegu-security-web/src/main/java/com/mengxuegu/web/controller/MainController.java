package com.mengxuegu.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.Map;

@Controller
public class MainController {
    @RequestMapping({"index", "/", ""})
    public String index(Map<String, Object> map) {
        //获取用户认证信息
        //第一种方式:
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null && principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            System.out.println("username" + authorities);
            String username = userDetails.getUsername();
            map.put("username", username);
        }
        return "index";//resources/tempates/index
    }

    /**
     * 获取用户认证信息
     * 第二种方式:
     *
     * @param authentication
     * @return
     */
    @RequestMapping("user/info")
    @ResponseBody
    public Object userInfo(Authentication authentication) {
        return authentication.getPrincipal();
    }

    /**
     * 获取用户认证信息
     * 第三种方式:
     *
     * @param userDetails
     * @return
     */
    @RequestMapping("user/info2")
    @ResponseBody
    public Object userInfo2(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }
}
