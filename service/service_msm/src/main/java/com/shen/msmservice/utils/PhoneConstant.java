package com.shen.msmservice.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: shenge
 * @Date: 2020-05-09 10:26
 * <p>
 * 创建常量类:电话短信服务的常量
 * 要注意：如果这样写其他类是拿不到值的，但是value想不用拿到对象就能那到值，只能使用static，但是@value不能注入static，需要使用spring提供的接口。
 */

@Component
public class PhoneConstant implements InitializingBean {


    @Value("${aliyun.oss.file.key_id}")
    private String keyId;

    @Value("${aliyun.oss.file.key_secret}")
    private String keysecret;

    @Value("${aliyun.message.sign}")
    private String sign;

    @Value("${aliyun.message.module}")
    private String module;


    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String KEY_SIGN;
    public static String KEY_MODULE;

    //在bean实例化对象之后执行。
    @Override
    public void afterPropertiesSet() throws Exception {
        KEY_ID = this.keyId;
        KEY_SECRET = this.keysecret;
        KEY_SIGN = this.sign;
        KEY_MODULE = this.module;
    }
}
