package com.shen.msmservice.service.impl;

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
import com.shen.msmservice.service.MsmService;
import com.shen.msmservice.utils.PhoneConstant;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: shenge
 * @Date: 2020-05-19 14:49
 */
@Service
public class MsmServiceImpl implements MsmService {
    /**
     * 发送短信
     *
     * @param phone
     * @param code
     * @return
     */
    @Override
    public Boolean sendMessage(String phone, String code) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", PhoneConstant.KEY_ID, PhoneConstant.KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        //固定的部分
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        //自己设置的部分
        request.putQueryParameter("RegionId", "cn-hangzhou");  //固定
        request.putQueryParameter("PhoneNumbers", phone);  //电话
        request.putQueryParameter("SignName", "大司在线教育网站");   //注册后的签名
        request.putQueryParameter("TemplateCode", PhoneConstant.KEY_MODULE);  //模板名称
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map));  //传入验证码：转json发送
        try {
            CommonResponse response = client.getCommonResponse(request);
//            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}
