package com.shen.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shen.eduservice.entity.EduCourse;
import com.shen.eduservice.entity.EduCourseDescription;
import com.shen.eduservice.entity.frontvo.CourseFrontVo;
import com.shen.eduservice.entity.frontvo.CourseWebInfo;
import com.shen.eduservice.entity.vo.CourseInfo;
import com.shen.eduservice.entity.vo.CoursePublishVo;
import com.shen.eduservice.mapper.EduCourseMapper;
import com.shen.eduservice.service.IEduCourseDescriptionService;
import com.shen.eduservice.service.IEduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shen.servicebase.exception.CustomizeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author shen
 * @since 2020-05-11
 */
@Transactional
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements IEduCourseService {

    @Autowired
    IEduCourseDescriptionService eduCourseDescriptionService;


    //添加课程信息
    @Override
    public String addOrUpdateCourse(CourseInfo courseInfo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfo, eduCourse);
        boolean save = this.saveOrUpdate(eduCourse);
        if (!save) {   //如果添加课程基本信息成功了，再添加课程描述
            throw new CustomizeException(20001, "添加或修改课程信息数据错误");
        }

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfo, eduCourseDescription);
        String courseId = eduCourse.getId();
        eduCourseDescription.setId(courseId);
        boolean save1 = eduCourseDescriptionService.saveOrUpdate(eduCourseDescription);
        if (!save1) {
            throw new CustomizeException(20001, "添加或修改课程描述数据错误");
        }
        return courseId;
    }

    // 取出某个课程信息。
    @Override
    public CourseInfo getCourseById(String courseId) {
        QueryWrapper<EduCourse> eduCourseQueryWrapper = new QueryWrapper<>();
        eduCourseQueryWrapper.eq("id", courseId);
        EduCourse eduCourse = this.getOne(eduCourseQueryWrapper);

        QueryWrapper<EduCourseDescription> eduCourseDescription = new QueryWrapper<>();
        eduCourseDescription.eq("id", courseId);
        EduCourseDescription eduCourseDes = eduCourseDescriptionService.getOne(eduCourseDescription);

        CourseInfo courseInfo = new CourseInfo();
        BeanUtils.copyProperties(eduCourse, courseInfo);
        courseInfo.setDescription(eduCourseDes.getDescription());

        return courseInfo;
    }

    @Override
    public CoursePublishVo getPublishInfo(String courseId) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(courseId);
        return publishCourseInfo;
    }

    //根据讲师id查询所讲课程
    @Override
    public List<EduCourse> getCourseByTeacherId(String teacherId) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id",teacherId);
        List<EduCourse> list = this.list(queryWrapper);
        return list;
    }

    /**
     * 条件分页查询课程
     * @param eduCoursePage
     * @param courseFrontVo
     * @return
     */
    @Override
    public Map<String, Object> getCourseList(Page<EduCourse> eduCoursePage, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        //判断存在、排序
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){
            queryWrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())){
            queryWrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())){
            queryWrapper.orderByDesc("price",courseFrontVo.getPriceSort());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){
            queryWrapper.orderByDesc("buy_count",courseFrontVo.getBuyCountSort());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())){
            queryWrapper.orderByDesc("gmt_create",courseFrontVo.getGmtCreateSort());
        }

        this.page(eduCoursePage,queryWrapper);

        Map<String, Object> map = new HashMap<>();
        List<EduCourse> records = eduCoursePage.getRecords();
        long current = eduCoursePage.getCurrent();
        long total = eduCoursePage.getTotal();
        long size = eduCoursePage.getSize();
        long pages = eduCoursePage.getPages();
        boolean hasNext = eduCoursePage.hasNext();
        boolean hasPrevious = eduCoursePage.hasPrevious();
        map.put("items",records);
        map.put("current",current);
        map.put("total",total);
        map.put("pages",pages);
        map.put("size",size);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);

        return map;
    }

    /**
     * 返回课程详情。
     * @param courseId
     * @return
     */
    @Override
    public CourseWebInfo getCourseInfo(String courseId) {
        return baseMapper.getCourseInfo(courseId);
    }

}
