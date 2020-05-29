package com.shen.msmservice.controller;

import com.shen.commonutils.RandomUtil;
import com.shen.commonutils.Result;
import com.shen.msmservice.service.MsmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @Author: shenge
 * @Date: 2020-05-19 14:46
 */
@Api(description = "短信发送服务")
@RestController
@RequestMapping("/edumsm/msm")
//@CrossOrigin
public class MsmController {

    @Autowired
    MsmService msmService;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    /**
     * 发送短信的方法：阿里云不会生成验证码，程序中生成验证码。
     * 将验证码和手机号发给阿里云的短信服务，它帮你发到手机中。
     *
     * @param phone ：传入的是手机号，进行验证码获取
     * @return
     */
    @GetMapping("/send/{phone}")
    public Result sendMsm(@PathVariable("phone") String phone) {
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return Result.ok();
        }
        code = RandomUtil.getFourBitRandom();
        Boolean isSuccess = msmService.sendMessage(phone, code);
        if (isSuccess) {
            //我们需要将code和phone存入redis中，如果用户在验证码失效时间内重复让后台发送验证码，直接把redis的给他返回。
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);  //设置时间为5分钟
            return Result.ok();
        }
        return Result.error().message("发送验证码错误");
    }
}
