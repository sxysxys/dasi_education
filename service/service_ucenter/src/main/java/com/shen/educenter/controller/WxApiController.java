package com.shen.educenter.controller;

import com.google.gson.Gson;
import com.shen.commonutils.JwtUtils;
import com.shen.educenter.entity.UcenterMember;
import com.shen.educenter.service.IUcenterMemberService;
import com.shen.educenter.utils.HttpClientUtils;
import com.shen.educenter.utils.WxConstant;
import com.shen.servicebase.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: shenge
 * @Date: 2020-05-21 00:10
 */
@Controller
@RequestMapping("/api/ucenter/wx")
//@CrossOrigin
public class WxApiController {

    @Autowired
    IUcenterMemberService ucenterMemberService;

    /**
     * 重定向到扫描二维码界面
     * @return
     */
    @GetMapping("login")
    public String getWxCode(){
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //对redirect_uri进行编码
        String encode = null;
        try {
            encode = URLEncoder.encode(WxConstant.REDIRECT_URL, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = String.format(baseUrl, WxConstant.WX_APP_ID, encode, "snsapi_login");
        return "redirect:"+url;
    }

    /**
     * 通过内网穿透重定向：拿到相应的code和state。
     * @param code
     * @param state
     * @return
     */
    @GetMapping("callback")
    public String callback(String code,String state){
        try {
            // 拼接出获取access_token的url
            String baseCallBackUrl=
                    "https://api.weixin.qq.com/sns/oauth2/access_token" +
                            "?appid=%s" +
                            "&secret=%s" +
                            "&code=%s" +
                            "&grant_type=authorization_code";

            String backUrl = String.format(baseCallBackUrl,WxConstant.WX_APP_ID,WxConstant.WX_APP_SECRET,code);

            //通过httpclient模拟客户端，传入路径返回access_token和相关信息。
            String accessToken = HttpClientUtils.get(backUrl);

            //将传回来的accessToken（json字符串）转换成map集合。
            Gson gson = new Gson();
            Map tokenMap = gson.fromJson(accessToken, HashMap.class);
            String token = (String) tokenMap.get("access_token");   //取到的token默认有效时间2小时
            String openid = (String) tokenMap.get("openid");

            //拿到以后需要进行判断，看是否加入数据库。如果存在数据就不需要再使用token去请求微信服务器了。
            UcenterMember ucenterMember = ucenterMemberService.getByopenId(openid);
            if (StringUtils.isEmpty(ucenterMember)){
                //通过token去调用api获取用户信息
                String accessTokenUrl=
                        "https://api.weixin.qq.com/sns/userinfo" +
                                "?access_token=%s" +
                                "&openid=%s";
                String tokenUrl = String.format(accessTokenUrl,token,openid);
                String userInfo = HttpClientUtils.get(tokenUrl);
//            System.out.println(userInfo);
                Map userMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String)userMap.get("nickname");  //获取昵称
                String headimgurl = (String)userMap.get("headimgurl");  //获取头像

                ucenterMember=new UcenterMember();
                ucenterMember.setNickname(nickname);
                ucenterMember.setAvatar(headimgurl);
                ucenterMember.setOpenid(openid);
                ucenterMemberService.save(ucenterMember);
            }

            String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());


            //重定向会页面进行渲染
            return "redirect:http://localhost:3000?token="+jwtToken;

        } catch (Exception e) {
            throw new CustomizeException(20001,"微信登录失败");
        }

    }
}
