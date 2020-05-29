package com.shen.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shen.commonutils.JwtUtils;
import com.shen.commonutils.MD5;
import com.shen.educenter.entity.UcenterMember;
import com.shen.educenter.entity.vo.RegisterVo;
import com.shen.educenter.mapper.UcenterMemberMapper;
import com.shen.educenter.service.IUcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shen.servicebase.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author shen
 * @since 2020-05-19
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements IUcenterMemberService {

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    /**
     * 手机号密码登录
     * @param ucenterMember
     * @return
     */
    @Override
    public String login(UcenterMember ucenterMember) {
        String mobile = ucenterMember.getMobile();
        String password = ucenterMember.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new CustomizeException(20001,"手机登录失败");
        }
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        UcenterMember member = this.getOne(queryWrapper);
        if (StringUtils.isEmpty(member)){
            throw new CustomizeException(20001,"手机登录失败");
        }
        if (!member.getPassword().equals(MD5.encrypt(password))){
            throw new CustomizeException(20001,"手机登录失败");
        }
        if (member.getIsDeleted()){
            throw new CustomizeException(20001,"手机登录失败");
        }

        //登录成功
        String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return jwtToken;
    }

    /**
     * 手机号注册
     * @param registerVo
     */
    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();    //注册验证码
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile)
        || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)){
            throw new CustomizeException(20001,"手机注册失败");
        }
        //验证码是否在5分钟内对的上
        String validate = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(validate)){
            throw new CustomizeException(20001,"手机注册失败");
        }
        //查询是否有手机号重复注册
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        int count = this.count(queryWrapper);
        if (count>0){
            //此时出现重复注册
            throw new CustomizeException(20001,"手机号已注册");
        }
        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setMobile(mobile);
        ucenterMember.setNickname(nickname);
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setIsDisabled(false);
        boolean save = this.save(ucenterMember);
        if (!save){
            throw new CustomizeException(20001,"手机注册失败");
        }
    }

    @Override
    public UcenterMember getByopenId(String openid) {
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid",openid);
        UcenterMember ucenterMember = this.getOne(queryWrapper);
        return ucenterMember;
    }

    @Override
    public Integer countRegister(String date) {
       return baseMapper.countRegister(date);
    }
}
