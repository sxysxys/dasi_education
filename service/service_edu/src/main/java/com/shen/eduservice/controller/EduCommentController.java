package com.shen.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netflix.client.http.HttpRequest;
import com.shen.commonutils.JwtUtils;
import com.shen.commonutils.Result;
import com.shen.commonutils.UcenterMember;
import com.shen.eduservice.cilent.ucenter.UcenterClient;
import com.shen.eduservice.entity.EduComment;
import com.shen.eduservice.service.IEduCommentService;
import com.shen.servicebase.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author shen
 * @since 2020-05-22
 */
@RestController
@RequestMapping("/eduservice/comment")
//@CrossOrigin
public class EduCommentController {

    @Autowired
    IEduCommentService eduCommentService;

    @Autowired
    UcenterClient ucenterClient;

    //将课程号传入得到评论信息
    @GetMapping("getComment/{page}/{limit}")
    public Result getComment(@PathVariable("page") long current,@PathVariable("limit") long limit,
                             String courseId){
        Page<EduComment> eduCommentPage = new Page<>(current,limit);
        Map<String, Object> eduComment =eduCommentService.getCommentByCourseId(eduCommentPage,courseId);
        return Result.ok().data(eduComment);
    }

    @PostMapping("save")
    public Result save(@RequestBody EduComment eduComment, HttpServletRequest request){
        String memberIdByJwtToken;
        try {
            memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        }catch (Exception e){
            throw new CustomizeException(28004,"请登录");
        }
        //只要登录超时，就会为空。
        if (StringUtils.isEmpty(memberIdByJwtToken)){
            return Result.error().code(28004).message("请登录");
        }

        eduComment.setMemberId(memberIdByJwtToken);
        UcenterMember member = ucenterClient.getInfoById(memberIdByJwtToken);
        eduComment.setAvatar(member.getAvatar());
        eduComment.setNickname(member.getNickname());
        boolean save = eduCommentService.save(eduComment);
        if (save){
            return Result.ok();
        }else {
            return Result.error().message("评论失败");
        }
    }



}

