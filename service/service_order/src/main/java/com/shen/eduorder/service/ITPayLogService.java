package com.shen.eduorder.service;

import com.shen.eduorder.entity.TPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author shen
 * @since 2020-05-23
 */
public interface ITPayLogService extends IService<TPayLog> {

    Map<String, Object> createNative(String orderNo);

    Map<String, String> queryStatus(String orderNo);

    void updateOrderStatus(Map<String, String> map);
}
