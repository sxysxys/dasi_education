package com.shen.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shen.commonutils.JwtUtils;
import com.shen.commonutils.Result;
import com.shen.eduorder.entity.TOrder;
import com.shen.eduorder.service.ITOrderService;
import com.shen.servicebase.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author shen
 * @since 2020-05-23
 */
@RestController
@RequestMapping("/eduorder/order")
//@CrossOrigin
public class TOrderController {

    @Autowired
    ITOrderService orderService;

    /**
     * 生成订单
     * @param courseId
     * @return
     */
    @PostMapping("addorder/{courseId}")
    public Result addOrder(@PathVariable("courseId") String courseId, HttpServletRequest request){
        String token;
        try{
            token = JwtUtils.getMemberIdByJwtToken(request);
        }catch (Exception e){
            throw new CustomizeException(28004,"请重新登录");
        }
        String orderId = orderService.addOrder(courseId,token);
        return Result.ok().data("orderId",orderId);
    }

    /**
     * 根据订单号查询出订单。
     * @param orderId
     * @return
     */
    @GetMapping("getOrderInfo/{orderId}")
    public Result getOrderInfo(@PathVariable("orderId") String orderId){
        QueryWrapper<TOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no",orderId);
        TOrder order = orderService.getOne(queryWrapper);
        return Result.ok().data("order",order);
    }

    /**
     * 查询是否已经支付。
     * @return
     */
    @GetMapping("isPay/{courseId}/{memberId}")
    public boolean isPay(@PathVariable("courseId")String courseId,@PathVariable("memberId")String memberId){

        boolean ispay=orderService.isPay(courseId,memberId);

        return ispay;

    }
}

