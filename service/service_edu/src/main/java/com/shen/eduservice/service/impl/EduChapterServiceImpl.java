package com.shen.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shen.eduservice.entity.EduChapter;
import com.shen.eduservice.entity.EduVideo;
import com.shen.eduservice.entity.chapter.ChapterVo;
import com.shen.eduservice.entity.chapter.VideoVo;
import com.shen.eduservice.mapper.EduChapterMapper;
import com.shen.eduservice.service.IEduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shen.eduservice.service.IEduVideoService;
import com.shen.servicebase.exception.CustomizeException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author shen
 * @since 2020-05-11
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements IEduChapterService {

    @Autowired
    IEduVideoService eduVideoService;


    //拿到章节和小节的树形结构，流进行优化。
    @Override
    public List<ChapterVo> getAllChapterAndVideo(String courseId) {
        //先取出所有章节
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", courseId);
        List<EduChapter> chapterList = this.list(chapterQueryWrapper);

        //再取出所有小节
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        List<EduVideo> list = eduVideoService.list(videoQueryWrapper);

//        List<ChapterVo> chapterVos = new ArrayList<>();

//        String eduChapterId;
//        //遍历章节封装
//        for (EduChapter eduChapter : chapterList) {
//            ChapterVo chapterVo = new ChapterVo();
//            List<VideoVo> videoVos = new ArrayList<>();
//            eduChapterId = eduChapter.getId();
//            for (EduVideo eduVideo : list) {
//                if (eduVideo.getChapterId().equals(eduChapterId)){
//                    VideoVo videoVo = new VideoVo();
//                    BeanUtils.copyProperties(eduVideo,videoVo);
//                    videoVos.add(videoVo);
//                }
//            }
//            chapterVo.setId(eduChapter.getId());
//            chapterVo.setTitle(eduChapter.getTitle());
//            chapterVo.setVideoVos(videoVos);
//            chapterVos.add(chapterVo);
//        }

        //第一层封装
        List<ChapterVo> collect = chapterList.stream().map(i -> {
            ChapterVo chapterVo = new ChapterVo();
            chapterVo.setId(i.getId());
            chapterVo.setTitle(i.getTitle());
            return chapterVo;
        }).collect(Collectors.toCollection(ArrayList::new));

        for (ChapterVo chapterVo : collect) {
            List<VideoVo> videoVos = new ArrayList<>();
            for (EduVideo eduVideo : list) {
                if (eduVideo.getChapterId().equals(chapterVo.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoVos.add(videoVo);
                }
            }
            chapterVo.setVideoVos(videoVos);
        }
        return collect;
    }

    /**
     * 删除章节：如果章节下面有小节不删除。
     *
     * @param chapterId
     */
    @Override
    public void deleteByChapterId(String chapterId) {
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id", chapterId);
        int count = eduVideoService.count(videoQueryWrapper);
        if (count > 0) {
            throw new CustomizeException(20001, "不能删除此章节");
        } else {
            boolean b = this.removeById(chapterId);
            if (!b) {
                throw new CustomizeException(20001, "删除失败");
            }
        }
    }
}
