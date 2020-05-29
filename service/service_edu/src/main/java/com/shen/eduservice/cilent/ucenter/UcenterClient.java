package com.shen.eduservice.cilent.ucenter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: shenge
 * @Date: 2020-05-22 22:42
 *
 * 使用memberid获取用户信息。
 */

@Component
@FeignClient(name="service-ucenter",fallback = UcenterFeignClient.class)
public interface UcenterClient {
    @GetMapping("/educenter/member/getInfoById/{userId}")
    public com.shen.commonutils.UcenterMember getInfoById(@PathVariable("userId") String userId);
}
