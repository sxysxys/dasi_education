package com.shen.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shen.eduservice.entity.EduComment;
import com.shen.eduservice.mapper.EduCommentMapper;
import com.shen.eduservice.service.IEduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author shen
 * @since 2020-05-22
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements IEduCommentService {

    @Override
    public Map<String, Object> getCommentByCourseId(Page<EduComment> eduCommentPage, String courseId) {
        QueryWrapper<EduComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        this.page(eduCommentPage,queryWrapper);

        //拿到所有评论和页码信息
        List<EduComment> records = eduCommentPage.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", eduCommentPage.getCurrent());
        map.put("pages", eduCommentPage.getPages());
        map.put("size", eduCommentPage.getSize());
        map.put("total", eduCommentPage.getTotal());
        map.put("hasNext", eduCommentPage.hasNext());
        map.put("hasPrevious", eduCommentPage.hasPrevious());

        return map;

    }
}
