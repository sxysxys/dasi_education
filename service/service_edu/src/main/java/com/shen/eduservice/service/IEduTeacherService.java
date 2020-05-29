package com.shen.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shen.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author shen
 * @since 2020-05-05
 */
public interface IEduTeacherService extends IService<EduTeacher> {

    Map<String, Object> getTeacherFrontList(Page<EduTeacher> teacherPage);
}
