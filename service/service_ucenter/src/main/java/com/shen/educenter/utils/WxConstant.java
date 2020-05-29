package com.shen.educenter.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: shenge
 * @Date: 2020-05-20 21:33
 */
@Component
public class WxConstant implements InitializingBean {

    @Value("${wx.open.app_id}")
    private String wxAppid;

    @Value("${wx.open.app_secret}")
    private String wxSecret;

    @Value("${wx.open.redirect_url}")
    private String redirectUrl;

    public static String WX_APP_ID;
    public static String WX_APP_SECRET;
    public static String REDIRECT_URL;

    //在bean实例化对象之后执行。
    @Override
    public void afterPropertiesSet() throws Exception {
        WX_APP_ID = this.wxAppid;
        WX_APP_SECRET = this.wxSecret;
        REDIRECT_URL = this.redirectUrl;
    }

}
