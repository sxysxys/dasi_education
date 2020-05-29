package com.shen.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author: shenge
 * @Date: 2020-05-09 10:26
 * <p>
 * 创建常量类
 * 要注意：如果这样写其他类是拿不到值的，但是value想不用拿到对象就能那到值，只能使用static，但是@value不能注入static，需要使用spring提供的接口。
 */

@Component
public class KeyAndSecret implements InitializingBean {


    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String keysecret;


    public static String KEY_ID;
    public static String KEY_SECRET;

    //在bean实例化对象之后执行。
    @Override
    public void afterPropertiesSet() throws Exception {
        KEY_ID = this.keyId;
        KEY_SECRET = this.keysecret;
    }
}
