package com.shen.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shen.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shen.eduservice.entity.frontvo.CourseFrontVo;
import com.shen.eduservice.entity.frontvo.CourseWebInfo;
import com.shen.eduservice.entity.vo.CourseInfo;
import com.shen.eduservice.entity.vo.CoursePublishVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author shen
 * @since 2020-05-11
 */
public interface IEduCourseService extends IService<EduCourse> {

    String addOrUpdateCourse(CourseInfo courseInfo);

    CourseInfo getCourseById(String courseId);

    CoursePublishVo getPublishInfo(String courseId);

    List<EduCourse> getCourseByTeacherId(String teacherId);

    Map<String, Object> getCourseList(Page<EduCourse> eduCoursePage, CourseFrontVo courseFrontVo);

    CourseWebInfo getCourseInfo(String courseId);
}
