package com.shen.staservice.client.ucenter;

import com.shen.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: shenge
 * @Date: 2020-05-25 09:06
 */
@FeignClient(name="service-ucenter",fallback = UcenterFeign.class)
@Component
public interface UcenterClient {

    @GetMapping("/educenter/member/countRegister/{date}")
    public Result countRegister(@PathVariable("date") String date);
}
