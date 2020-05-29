package com.shen.msmservice.send;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.shen.commonutils.RandomUtil;
import com.shen.msmservice.utils.PhoneConstant;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: shenge
 * @Date: 2020-05-19 16:28
 * 短信服务验证。
 */
@SpringBootTest
public class SendMessage {

    /**
     * 发送消息测试
     */
    @Test
    public void sendMessage() {
        Boolean isSuccess = send(PhoneConstant.KEY_ID, PhoneConstant.KEY_SECRET);
        System.out.println(isSuccess);
    }

    public static Boolean send(String accessId, String accessSecret) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        //固定的部分
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        //自己设置的部分
        request.putQueryParameter("RegionId", "cn-hangzhou");  //固定
        request.putQueryParameter("PhoneNumbers", "13075595172");  //电话
        request.putQueryParameter("SignName", "大司在线教育网站");   //注册后的签名
        request.putQueryParameter("TemplateCode", "SMS_190720225");  //模板名称
        String code = RandomUtil.getFourBitRandom();
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map));  //传入验证码：转json发送
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }

}
