package com.shen.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shen.eduservice.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author shen
 * @since 2020-05-22
 */
public interface IEduCommentService extends IService<EduComment> {

    Map<String, Object> getCommentByCourseId(Page<EduComment> eduCommentPage, String courseId);
}
