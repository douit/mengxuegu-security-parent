package com.mengxuegu.security.authentication.mobile.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

public class SMSUtil {
    //对应你阿里云账户的 accessKeyId
    private static final String accessKeyId = "LTAI4FuskWi1nk9WaWo9Hbt4";
    //对应你阿里云账户的 accessKeySecret
    private static final String accessKeySecret = "ffMdwutNCXXGoKJkwIEj3n2TXLUVcM";
    //对应签名名称
    private static final String signName = "实惠商城";
    //对应模板代码
    private static final String templateCode = "SMS_180049905";
    //对应验证码
//    private static int mobile_code = (int) ((Math.random() * 9 + 1) * 100000);

    /**
     * 短信发送
     * @param telphone 发送的手机号
     * @param mobileCode 验证码
     */
    public static void SendCode(String telphone,int mobileCode) {

        DefaultProfile profile = DefaultProfile.getProfile("default",
                accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        //阿里云对应发送短信的服务器地址
        request.setDomain("dysmsapi.aliyuncs.com");
        //对应的版本号
        request.setVersion("2017-05-25");

        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", telphone);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", "{\"code\":" + mobileCode + "}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

}
