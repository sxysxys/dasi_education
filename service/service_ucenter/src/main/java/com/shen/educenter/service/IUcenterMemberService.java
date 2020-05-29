package com.shen.educenter.service;

import com.shen.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shen.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author shen
 * @since 2020-05-19
 */
public interface IUcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember ucenterMember);

    void register(RegisterVo registerVo);

    UcenterMember getByopenId(String openid);

    Integer countRegister(String date);
}
