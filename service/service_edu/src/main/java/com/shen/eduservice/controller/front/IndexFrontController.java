package com.shen.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shen.commonutils.Result;
import com.shen.eduservice.entity.EduCourse;
import com.shen.eduservice.entity.EduTeacher;
import com.shen.eduservice.service.IEduCourseService;
import com.shen.eduservice.service.IEduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: shenge
 * @Date: 2020-05-16 16:57
 * <p>
 * 前台主页显示
 */
@RestController
@RequestMapping("/eduservice/indexfront")
//@CrossOrigin
public class IndexFrontController {

    @Autowired
    IEduCourseService eduCourseService;

    @Autowired
    IEduTeacherService eduTeacherService;

    //查询前8条热门课程，前4个名师
    @GetMapping("findHotCourse")
    public Result getHotCourse() {
        QueryWrapper<EduCourse> eduCourseQueryWrapper = new QueryWrapper<>();
        eduCourseQueryWrapper.orderByDesc("id");
        eduCourseQueryWrapper.last("limit 8");
        List<EduCourse> eduCourses = eduCourseService.list(eduCourseQueryWrapper);

        QueryWrapper<EduTeacher> eduTeacherQueryWrapper = new QueryWrapper<>();
        eduTeacherQueryWrapper.orderByDesc("id");
        eduTeacherQueryWrapper.last("limit 4");
        List<EduTeacher> eduTeachers = eduTeacherService.list(eduTeacherQueryWrapper);

        return Result.ok().data("courses", eduCourses).data("teachers", eduTeachers);
    }
}
