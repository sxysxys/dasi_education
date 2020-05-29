package com.shen.eduservice.cilent.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: shenge
 * @Date: 2020-05-24 14:57
 */
@FeignClient(name="service-order",fallback = OrderFeignClient.class)
@Component
public interface OrderClient {

    @GetMapping("/eduorder/order/isPay/{courseId}/{memberId}")
    public boolean isPay(@PathVariable("courseId")String courseId, @PathVariable("memberId")String memberId);
}
