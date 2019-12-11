package com.mengxuegu.security.properites;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mengxuegu.security")
public class SecurityProperties {
    //会将mengxuegu.security.authentication 下面的值绑定到AuthenticationProperties对象中
    private AuthenticationProperties authentication;

    public AuthenticationProperties getAuthentication() {
        return authentication;
    }

    public void setAuthentication(AuthenticationProperties authentication) {
        this.authentication = authentication;
    }
}
