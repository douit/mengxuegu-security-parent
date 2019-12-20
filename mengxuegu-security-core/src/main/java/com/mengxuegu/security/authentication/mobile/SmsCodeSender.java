package com.mengxuegu.security.authentication.mobile;

import com.mengxuegu.security.authentication.mobile.util.SMSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 发送短信验证码，第三方短信服务接口
 */

public class SmsCodeSender implements SmsSend {
    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * @param moile   手机号
     * @param content 发送的内容:接收的是验证码
     * @return
     */
    @Override
    public boolean sendSms(String moile, String content) {
        String sendContent = String.format("验证码%s,请勿泄露给别人。", content);
        int mobileCode = Integer.parseInt(content);
        System.out.println(moile);
        //调用第三方的发送功能的SDK
        SMSUtil.SendCode(moile, mobileCode);

        logger.info("向手机号" + moile + "发送的短信为" + sendContent);
        return true;
    }
}
