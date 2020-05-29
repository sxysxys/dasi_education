package com.shen.eduservice.cilent.ucenter;

import org.springframework.stereotype.Component;

/**
 * @Author: shenge
 * @Date: 2020-05-22 22:42
 */
@Component
public class UcenterFeignClient implements UcenterClient{
    @Override
    public com.shen.commonutils.UcenterMember getInfoById(String userId) {
        return null;
    }
}
