package com.shen.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shen.commonutils.Result;
import com.shen.eduservice.entity.EduCourse;
import com.shen.eduservice.entity.EduTeacher;
import com.shen.eduservice.service.IEduCourseService;
import com.shen.eduservice.service.IEduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: shenge
 * @Date: 2020-05-21 17:08
 *
 * 前台讲师查询
 */
@RestController
@RequestMapping("/eduservice/teacherfront")
//@CrossOrigin
public class TeacherFrontController {

    @Autowired
    IEduTeacherService eduTeacherService;

    @Autowired
    IEduCourseService eduCourseService;

    /**
     * 不使用前台组件查询讲师列表
     * @param current
     * @param limit
     * @return
     */
    @PostMapping("getTeacherFrontList/{current}/{limit}")
    public Result getTeacherFrontList(@PathVariable("current") long current,@PathVariable("limit") long limit){
        Page<EduTeacher> teacherPage = new Page<>(current, limit);
        Map<String,Object> map=eduTeacherService.getTeacherFrontList(teacherPage);
        return Result.ok().data(map);
    }

    /**
     * 通过讲师id查询讲师详情
     * @param teacherId
     * @return
     */
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public Result getTeacherFrontInfo(@PathVariable("teacherId") String teacherId){
        //查询讲师基本信息
        EduTeacher teacher = eduTeacherService.getById(teacherId);
        //查询讲师所讲课程信息
        List<EduCourse> eduCourses = eduCourseService.getCourseByTeacherId(teacherId);
        return Result.ok().data("teacher",teacher).data("courses",eduCourses);
    }

}
