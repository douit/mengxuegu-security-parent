package com.mengxuegu.security.controller;

import com.mengxuegu.base.result.MengxueguResult;
import com.mengxuegu.security.authentication.mobile.SmsSend;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 关于手机登录控制层
 */
@Controller
public class MobileLoginController {
    public static final String SESSION_KEY = "SESSION_KEY_MOBILE_CODE";

    /**
     * 前往手机验证码登录页
     *
     * @return
     */
    @RequestMapping("/mobile/page")
    public String toMobilePage() {
        return "login-mobile";
    }

    @Autowired
    SmsSend smsSend;

    /**
     * 发送手机验证码
     *
     * @return
     */
    @RequestMapping("/code/mobile")
    @ResponseBody
    public MengxueguResult smsCode(HttpServletRequest request) {
        //获取手机号码
        String mobile = request.getParameter("mobile");
        //验证手机号是否合法
        if (!isMobile(mobile)) {
            return MengxueguResult.build(203, "手机号码不正确");
        }
        //1.生成手机验证码
        String code = RandomStringUtils.randomNumeric(4);

        //2.把手机验证码保存到session中
        request.getSession().setAttribute(SESSION_KEY, code);

        //3.发送验证码到用户手机上
        smsSend.sendSms(mobile, code);
        return MengxueguResult.ok();
    }

    /**
     * 验收手机号码是否正确的正则表达式
     * @param mobile
     * @return
     */
    public Boolean isMobile(String mobile) {
        Matcher matcher = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$").matcher(mobile);
        return matcher.matches();
    }
}
