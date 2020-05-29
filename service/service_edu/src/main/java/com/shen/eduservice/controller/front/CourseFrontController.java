package com.shen.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shen.commonutils.JwtUtils;
import com.shen.commonutils.Result;
import com.shen.eduservice.cilent.order.OrderClient;
import com.shen.eduservice.entity.EduCourse;
import com.shen.eduservice.entity.chapter.ChapterVo;
import com.shen.eduservice.entity.frontvo.CourseFrontVo;
import com.shen.eduservice.entity.frontvo.CourseWebInfo;
import com.shen.eduservice.service.IEduChapterService;
import com.shen.eduservice.service.IEduCourseService;
import com.shen.servicebase.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author: shenge
 * @Date: 2020-05-21 17:08
 *
 * 前台课程查询
 */
@RestController
@RequestMapping("/eduservice/coursefront")
//@CrossOrigin
public class CourseFrontController {

    @Autowired
    IEduCourseService eduCourseService;

    @Autowired
    IEduChapterService eduChapterService;

    @Autowired
    OrderClient orderClient;

    /**
     * 条件分页查询课程
     * @param courseFrontVo
     * @return
     */
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public Result getFrontCourseList(@PathVariable("page") long page,
                                     @PathVariable("limit") long limit,
                                     @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> eduCoursePage = new Page<>(page, limit);
        Map<String,Object> courseMap = eduCourseService.getCourseList(eduCoursePage,courseFrontVo);
        return Result.ok().data(courseMap);
    }

    @GetMapping("getFrontCourseInfo/{courseId}")
    public Result getFrontCourseInfo(@PathVariable("courseId") String courseId,HttpServletRequest request){
        String memberId;
        try{
            memberId = JwtUtils.getMemberIdByJwtToken(request);
        }catch (Exception e){
            throw new CustomizeException(28004,"请重新登录");
        }

        //根据课程id拿到课程详情信息
        CourseWebInfo courseWebInfo=eduCourseService.getCourseInfo(courseId);

        //根据课程id拿到相应的章节和小节信息
        List<ChapterVo> allChapterAndVideo = eduChapterService.getAllChapterAndVideo(courseId);

        //通过系统调用去获取
        boolean pay = orderClient.isPay(courseId, memberId);


        return Result.ok().data("courseInfo",courseWebInfo).data("chapterAndVideo",allChapterAndVideo).data("isPay",pay);
    }

    /**
     * 测试
     * @param courseId
     * @param
     * @return
     */
    @GetMapping("getFrontCourseTest/{courseId}/{memberId}")
    public Result getFrontCourseTest(@PathVariable("courseId") String courseId,@PathVariable("memberId")String memberId){

        //通过系统调用去获取
        boolean pay = orderClient.isPay(courseId, memberId);


        return Result.ok().data("isPay",pay);
    }

}
