package com.shen.eduservice.controller;


import com.shen.commonutils.Result;
import com.shen.eduservice.entity.subject.OneSubject;
import com.shen.eduservice.service.IEduSubjectService;
import com.shen.eduservice.service.IEduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author shen
 * @since 2020-05-10
 */
@Api(description = "excel上传")
@RestController
@RequestMapping("/eduservice/edu-subject")
//@CrossOrigin
public class EduSubjectController {

    @Autowired
    IEduSubjectService eduSubjectService;

    @PostMapping("addSubject")
    @ApiOperation(value = "导入excel文件")
    public Result addSubject(@ApiParam(name = "file", value = "excel文件", required = true) MultipartFile file) {
        //上传得到的execl文件
        eduSubjectService.saveSubject(file);
        return Result.ok();
    }

    @GetMapping("listSubject")
    @ApiOperation(value = "树形结构返回subject")
    public Result listSubject() {
        List<OneSubject> oneSubjectList = eduSubjectService.listOneAndTwoSubject();
        return Result.ok().data("list", oneSubjectList);
    }

}

