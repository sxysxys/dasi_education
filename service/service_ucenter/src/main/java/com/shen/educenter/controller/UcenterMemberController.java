package com.shen.educenter.controller;


import com.shen.commonutils.JwtUtils;
import com.shen.commonutils.Result;
import com.shen.educenter.entity.UcenterMember;
import com.shen.educenter.entity.vo.RegisterVo;
import com.shen.educenter.service.IUcenterMemberService;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author shen
 * @since 2020-05-19
 */
@RestController
@RequestMapping("/educenter/member")
//@CrossOrigin
public class UcenterMemberController {

    @Autowired
    IUcenterMemberService ucenterMemberService;

    @PostMapping("login")
    public Result phoneLogin(@RequestBody UcenterMember ucenterMember){
        String token=ucenterMemberService.login(ucenterMember);
        return Result.ok().data("token",token);
    }

    @PostMapping("register")
    public Result phoneRegister(@RequestBody RegisterVo registerVo){
        ucenterMemberService.register(registerVo);
        return Result.ok();
    }

    @GetMapping("getUserInfo")
    public Result getUserInfo(HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember byId = ucenterMemberService.getById(memberId);
        return Result.ok().data("userInfo",byId);
    }

    /**
     * 通过userId获取member对象，当远程调用的时候，不能直接返回对象，因为调用者没有这个对象，所以要创建一个共有的对象进行返回。
     * @param userId
     * @return
     */
    @GetMapping("getInfoById/{userId}")
    public com.shen.commonutils.UcenterMember getInfoById(@PathVariable("userId") String userId){
        UcenterMember member = ucenterMemberService.getById(userId);
        com.shen.commonutils.UcenterMember ucenterMember = new com.shen.commonutils.UcenterMember();
        BeanUtils.copyProperties(member,ucenterMember);
        return ucenterMember;
    }

    /**
     * 记录某日注册的人数
     */
    @GetMapping("countRegister/{date}")
    public Result countRegister(@PathVariable("date") String date){
        Integer count = ucenterMemberService.countRegister(date);
        return Result.ok().data("count",count);
    }

}

