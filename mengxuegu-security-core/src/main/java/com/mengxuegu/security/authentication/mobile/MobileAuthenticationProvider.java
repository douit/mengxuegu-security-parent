package com.mengxuegu.security.authentication.mobile;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 手机认证处理提供者
 */
public class MobileAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * 认证处理
     * 1.通过手机号码，查询用户信息(UserDetailsService实现)
     * 2.当查询到用户信息，则认为认证通过，封装Authentication对象
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobileauthenticationToken mobileauthenticationToken = (MobileauthenticationToken) authentication;
        //获取手机号码
        String mobile = (String) mobileauthenticationToken.getPrincipal();
        //通过手机号码，查询用户信息(UserDetailsService实现)
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(mobile);
        //未查询到用户信息
        if (userDetails == null) {
            throw new AuthenticationServiceException("该手机号为注册");
        }
        //认证通过
        //封装到 MobileauthenticationToken
        MobileauthenticationToken authenticationToken = new MobileauthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationToken.setDetails(mobileauthenticationToken.getDetails());
        return authenticationToken;
    }

    /**
     * 通过这个方法，来选择对应的Provider，即选择MobileAuthenticationProvider
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        //判断authentication是否属于MobileauthenticationToken类型
        boolean assignable = MobileauthenticationToken.class.isAssignableFrom(authentication);
        return assignable;
    }
}
