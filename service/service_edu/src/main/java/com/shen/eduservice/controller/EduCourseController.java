package com.shen.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shen.commonutils.Result;
import com.shen.commonutils.ordervo.CourseWebInfoOrder;
import com.shen.eduservice.constant.PublishStatus;
import com.shen.eduservice.entity.EduCourse;
import com.shen.eduservice.entity.frontvo.CourseWebInfo;
import com.shen.eduservice.entity.vo.CourseInfo;
import com.shen.eduservice.entity.vo.CoursePublishVo;
import com.shen.eduservice.entity.vo.CourseQuery;
import com.shen.eduservice.service.IEduCourseService;
import com.shen.servicebase.exception.CustomizeException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author shen
 * @since 2020-05-11
 */
@Api(description = "课程信息上传")
@RestController
@RequestMapping("/eduservice/course")
//@CrossOrigin
public class EduCourseController {

    @Autowired
    IEduCourseService eduCourseService;

    @GetMapping
    @ApiOperation(value = "获取课程列表")
    public Result getAllCourse() {
        List<EduCourse> eduCourses = eduCourseService.list(null);
        return Result.ok().data("courses", eduCourses);
    }

    @PostMapping("addCourseInfo")
    @ApiOperation(value = "导入课程信息表单")
    public Result addCourseInfo(@ApiParam(name = "courseInfo", value = "课程信息", required = true)
                                @RequestBody CourseInfo courseInfo) {
        String courseId = eduCourseService.addOrUpdateCourse(courseInfo);
        return Result.ok().data("courseId", courseId);
    }

    @GetMapping("getCourse/{id}")
    @ApiOperation(value = "拿到某个课程所有信息")
    public Result getCourseById(@PathVariable("id") String courseId) {
        CourseInfo courseInfo = eduCourseService.getCourseById(courseId);
        return Result.ok().data("course", courseInfo);
    }

    @PostMapping("updateCourseInfo/{id}")
    @ApiOperation(value = "更新某个课程信息")
    public Result updateCourseInfo(@ApiParam(name = "courseInfo", value = "课程信息", required = true)
                                   @RequestBody CourseInfo courseInfo,
                                   @PathVariable("id") String id) {
        courseInfo.setId(id);
        String courseId = eduCourseService.addOrUpdateCourse(courseInfo);
        return Result.ok().data("courseId", courseId);
    }

    @GetMapping("getPublishInfo/{courseId}")
    @ApiOperation(value = "发布前回显数据")
    public Result getPublishInfo(@PathVariable("courseId") String courseId) {
        CoursePublishVo courseInfo = eduCourseService.getPublishInfo(courseId);
        return Result.ok().data("publishInfo", courseInfo);
    }

    @GetMapping("publishCourse/{courseId}")
    @ApiOperation(value = "最终发布课程")
    public Result publishCourse(@PathVariable("courseId") String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus(PublishStatus.PUBLISH.getStatus());
        if (!eduCourseService.updateById(eduCourse)) {
            throw new CustomizeException(20001, "更新失败");
        }
        return Result.ok();
    }

    @PostMapping("pageCourseCondition/{current}/{limit}")
    @ApiOperation(value = "条件分页查询课程")
    public Result pageTeacherCondition(@ApiParam(name = "current", value = "当前页", required = true) @PathVariable Long current,
                                       @ApiParam(name = "limit", value = "一页的数量", required = true) @PathVariable Long limit,
                                       @RequestBody(required = false) CourseQuery courseQuery) {  //使用json传参。
        Page<EduCourse> eduCoursePage = new Page<>(current, limit);
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(courseQuery.getTitle())) {
            queryWrapper.like("title", courseQuery.getTitle());
        }
        if (!StringUtils.isEmpty(courseQuery.getStatus())) {
            queryWrapper.eq("status", courseQuery.getStatus());
        }

        queryWrapper.orderByDesc("gmt_create");
        eduCourseService.page(eduCoursePage, queryWrapper);
        long total = eduCoursePage.getTotal();
        List<EduCourse> records = eduCoursePage.getRecords();
        return Result.ok().data("total", total).data("rows", records);
    }

    @DeleteMapping("deleteCourse/{courseId}")
    @ApiOperation(value = "逻辑删除课程")
    public Result deleteCourse(@PathVariable("courseId") String courseId) {
        if (!eduCourseService.removeById(courseId)) {
            throw new CustomizeException(20001, "删除课程失败");
        }
        return Result.ok();
    }

    //远程调用查询课程信息接口。
    @GetMapping("getCourseOrder/{courseId}")
    public CourseWebInfoOrder getCourseOrder(@PathVariable("courseId")String courseId){
        CourseWebInfo courseInfo = eduCourseService.getCourseInfo(courseId);
        CourseWebInfoOrder eduCourseOrder = new CourseWebInfoOrder();
        BeanUtils.copyProperties(courseInfo,eduCourseOrder);
        return eduCourseOrder;
    }

}

