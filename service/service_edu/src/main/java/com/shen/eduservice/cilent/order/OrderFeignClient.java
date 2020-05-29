package com.shen.eduservice.cilent.order;

import org.springframework.stereotype.Component;

/**
 * @Author: shenge
 * @Date: 2020-05-24 16:02
 */
@Component
public class OrderFeignClient implements OrderClient{
    @Override
    public boolean isPay(String courseId, String memberId) {
        return false;
    }
}
