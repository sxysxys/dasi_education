package com.shen.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.google.gson.JsonObject;
import com.shen.eduorder.entity.TOrder;
import com.shen.eduorder.entity.TPayLog;
import com.shen.eduorder.mapper.TPayLogMapper;
import com.shen.eduorder.service.ITOrderService;
import com.shen.eduorder.service.ITPayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shen.eduorder.utils.HttpClient;
import com.shen.servicebase.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author shen
 * @since 2020-05-23
 */
@Service
public class TPayLogServiceImpl extends ServiceImpl<TPayLogMapper, TPayLog> implements ITPayLogService {

    @Autowired
    ITOrderService orderService;

    /**
     * 发送请求拿到支付二维码url。
     * @param orderNo
     * @return
     */
    @Override
    public Map<String, Object> createNative(String orderNo)  {
        try {
            //先通过订单号查询出相应的订单
            QueryWrapper<TOrder> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("order_no",orderNo);
            TOrder order = orderService.getOne(queryWrapper);

            //通过httpclient提交给微信服务器获取二维码的url，使用谷粒学院账号
            Map<String, String> map = new HashMap<>();
            map.put("appid","wx74862e0dfcf69954");
            map.put("mch_id","1558950191");
            map.put("nonce_str", WXPayUtil.generateNonceStr());  //随机生成字符串。
            map.put("body", order.getCourseTitle());
            map.put("out_trade_no", orderNo);
            map.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue() +"");  //金额，单位为分
            map.put("spbill_create_ip", "127.0.0.1");
            map.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
            map.put("trade_type", "NATIVE");

            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            client.setHttps(true);
            client.setXmlParam(WXPayUtil.generateSignedXml(map,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));//这个是微信控制台随时修改设置的，目的是将所有数据和控制台中的key再加密一次，判断和sign是否一样。
            client.post();  //由于使用了https，用到了ssl加密。
            String xml = client.getContent();

            //将取回的xml字符串重新变成map
            Map<String, String> toMap = WXPayUtil.xmlToMap(xml);

            Map m = new HashMap<>();
            m.put("out_trade_no", orderNo);
            m.put("course_id", order.getCourseId());
            m.put("total_fee", order.getTotalFee());
            m.put("result_code", toMap.get("result_code"));
            m.put("code_url", toMap.get("code_url"));

            return m;

        } catch (Exception e) {
            throw new CustomizeException(20001,"支付失败");
        }

    }

    /**
     * 根据订单号查询订单目前状态
     * @param orderNo
     * @return
     */
    @Override
    public Map<String, String> queryStatus(String orderNo) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("appid","wx74862e0dfcf69954");
            map.put("mch_id","1558950191");
            map.put("nonce_str", WXPayUtil.generateNonceStr());  //随机生成字符串。
            map.put("out_trade_no", orderNo);

            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setHttps(true);
            client.setXmlParam(WXPayUtil.generateSignedXml(map,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.post();
            String xml = client.getContent();

            Map<String, String> toMap = WXPayUtil.xmlToMap(xml);

            return toMap;

        } catch (Exception e) {
            throw new CustomizeException(20001,"查询订单状态失败");
        }

    }

    /**
     * 当支付成功的时候进入这里，插入支付日志，更新支付状态。
     * @param map
     */
    @Override
    public void updateOrderStatus(Map<String, String> map) {
        //先更新支付状态表
        String orderId = map.get("out_trade_no");
        QueryWrapper<TOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no",orderId);
        TOrder order = orderService.getOne(queryWrapper);
        if (order.getStatus()==1){
            return;
        }
        order.setStatus(1);
        orderService.updateById(order);

        try {
            String payTime = map.get("time_end");
            // DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            // LocalDateTime dateTime = LocalDateTime.parse(payTime, df);
            SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
            Date parse= sd.parse(payTime);
            //再插入支付日志
            TPayLog tPayLog = new TPayLog();
            tPayLog.setOrderNo(orderId);
            tPayLog.setTotalFee(order.getTotalFee());
            tPayLog.setTransactionId(map.get("transaction_id"));
            tPayLog.setTradeState(map.get("trade_state"));
            tPayLog.setPayType(1);
            tPayLog.setAttr(JSONObject.toJSONString(map));  //将所有属性都存到这里方便出问题查看。
            tPayLog.setPayTime(parse);
            this.save(tPayLog);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
