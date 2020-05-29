package com.shen.eduorder.service;

import com.shen.eduorder.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author shen
 * @since 2020-05-23
 */
public interface ITOrderService extends IService<TOrder> {

    String addOrder(String courseId, String userId);

    boolean isPay(String courseId, String token);
}
