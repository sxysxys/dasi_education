package com.shen.eduorder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shen.commonutils.UcenterMember;
import com.shen.commonutils.ordervo.CourseWebInfoOrder;
import com.shen.eduorder.client.course.CourseInfoClient;
import com.shen.eduorder.client.ucenter.UcenterClient;
import com.shen.eduorder.entity.TOrder;
import com.shen.eduorder.mapper.TOrderMapper;
import com.shen.eduorder.service.ITOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shen.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author shen
 * @since 2020-05-23
 */
@Transactional
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements ITOrderService {

    @Autowired
    CourseInfoClient courseInfoClient;

    @Autowired
    UcenterClient ucenterClient;

    /**
     * 通过课程id和用户id添加订单
     * @param courseId
     * @param userId
     * @return
     */
    @Override
    public String addOrder(String courseId, String userId) {
        //远程调用edu查询课程信息
        CourseWebInfoOrder courseOrder = courseInfoClient.getCourseOrder(courseId);
        //远程调用ucenter获得用户信息
        UcenterMember member = ucenterClient.getInfoById(userId);

        //添加order对象
        TOrder order = new TOrder();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseOrder.getTitle());
        order.setCourseCover(courseOrder.getCover());
        order.setTeacherName("test");
        order.setTotalFee(courseOrder.getPrice());
        order.setMemberId(userId);
        order.setMobile(member.getMobile());
        order.setNickname(member.getNickname());

        order.setStatus(0);  //未支付
        order.setPayType(1);  //微信支付
        baseMapper.insert(order);

        return order.getOrderNo();

    }

    @Override
    public boolean isPay(String courseId, String token) {
        QueryWrapper<TOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        queryWrapper.eq("member_id",token);
        queryWrapper.eq("status",1);
        int count = this.count(queryWrapper);
        if (count==0){
            return false;
        }
        return true;
    }
}
