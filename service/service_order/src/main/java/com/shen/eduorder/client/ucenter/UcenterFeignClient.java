package com.shen.eduorder.client.ucenter;

import com.shen.commonutils.UcenterMember;
import org.springframework.stereotype.Component;

/**
 * @Author: shenge
 * @Date: 2020-05-22 22:42
 */
@Component
public class UcenterFeignClient implements UcenterClient{
    @Override
    public UcenterMember getInfoById(String userId) {
        return null;
    }
}
