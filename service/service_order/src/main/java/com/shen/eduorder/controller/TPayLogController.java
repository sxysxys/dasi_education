package com.shen.eduorder.controller;


import com.shen.commonutils.Result;
import com.shen.eduorder.service.ITPayLogService;
import com.shen.servicebase.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author shen
 * @since 2020-05-23
 */
@RestController
@RequestMapping("/eduorder/paylog")
//@CrossOrigin
public class TPayLogController {

    @Autowired
    ITPayLogService payLogService;

    /**
     * 获取微信支付url
     * @param orderNo:订单id
     * @return
     */
    @GetMapping("createNative/{orderNo}")
    public Result createNative(@PathVariable("orderNo")String orderNo){
        Map<String,Object> map = payLogService.createNative(orderNo);
        return Result.ok().data(map);
    }

    /**
     * 通过订单号查询订单目前的状态，再根据订单状态进行更新数据库订单信息。
     * @param orderNo:订单id
     * @return
     */
    @GetMapping("queryStatus/{orderNo}")
    public Result queryStatus(@PathVariable("orderNo") String orderNo){
        //先去微信服务器查询本订单目前的状态。
        Map<String,String> map=payLogService.queryStatus(orderNo);
        if (map==null){
            throw new CustomizeException(20001,"获取订单失败");
        }
        if (map.get("trade_state").equals("SUCCESS")){  //此时代表订单支付成功
            payLogService.updateOrderStatus(map);
            return Result.ok().message("支付成功");
        }
        return Result.ok().code(25000).message("支付中");
    }

}

