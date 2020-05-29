package com.shen.eduservice.service;

import com.shen.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shen.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author shen
 * @since 2020-05-11
 */
public interface IEduChapterService extends IService<EduChapter> {

    List<ChapterVo> getAllChapterAndVideo(String courseId);

    void deleteByChapterId(String chapterId);
}
