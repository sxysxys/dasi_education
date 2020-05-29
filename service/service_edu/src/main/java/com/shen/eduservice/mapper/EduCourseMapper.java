package com.shen.eduservice.mapper;

import com.shen.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shen.eduservice.entity.frontvo.CourseWebInfo;
import com.shen.eduservice.entity.vo.CoursePublishVo;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author shen
 * @since 2020-05-11
 */
@Repository
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CoursePublishVo getPublishCourseInfo(String courseId);

    CourseWebInfo getCourseInfo(String courseId);
}
