package com.shen.eduservice.controller;


import com.shen.commonutils.Result;
import com.shen.eduservice.entity.EduChapter;
import com.shen.eduservice.entity.chapter.ChapterVo;
import com.shen.eduservice.service.IEduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(description = "章节控制器")
@RestController
@RequestMapping("/eduservice/chapter")
//@CrossOrigin
public class EduChapterController {

    @Autowired
    IEduChapterService eduChapterService;

    //送入的是课程id
    @GetMapping("getAllChapter/{id}")
    @ApiOperation(value = "获取所有章节小节")
    public Result allChapter(@ApiParam(name = "id", value = "课程号", required = true) @PathVariable("id") String courseId) {
        List<ChapterVo> list = eduChapterService.getAllChapterAndVideo(courseId);
        return Result.ok().data("allChapterAndVideo", list);
    }

    @PostMapping("addChapter")
    @ApiOperation(value = "添加章节")
    public Result addChapter(@ApiParam(name = "eduChapter", value = "章节", required = true) @RequestBody EduChapter eduChapter) {
        eduChapterService.save(eduChapter);  //这里肯定要返回一个id回去，要不然查询没法查。
        return Result.ok();
    }

    //送入的是章节id
    @GetMapping("getChapter/{chapterId}")
    @ApiOperation(value = "查询某一个章节")
    public Result getChapter(@ApiParam(name = "chapterId", value = "课程号", required = true) @PathVariable("chapterId") String chapterId) {
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return Result.ok().data("chapter", eduChapter);
    }

    @PostMapping("updateChapter")
    @ApiOperation(value = "更新章节")
    public Result updateChapter(@ApiParam(name = "eduChapter", value = "章节", required = true) @RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return Result.ok();
    }

    //TODO
    //直接点击删除章节是逻辑删除，定时任务执行物理删除。
    @DeleteMapping("deleteChapter/{chapterId}")
    @ApiOperation(value = "删除某一个章节")
    public Result deleteChapter(@PathVariable("chapterId") String chapterId) {
        eduChapterService.deleteByChapterId(chapterId);
        return Result.ok();
    }


}

