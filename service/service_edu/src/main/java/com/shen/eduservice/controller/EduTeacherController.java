package com.shen.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shen.commonutils.Result;
import com.shen.eduservice.entity.EduTeacher;
import com.shen.eduservice.entity.vo.TeacherQuery;
import com.shen.eduservice.service.IEduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author shen
 * @since 2020-05-05
 */
@Api(description = "讲师管理")  //这些api对功能没有影响，只是方便我们测试接口时查看。
@RestController
@RequestMapping("/eduservice/teacher")
//@CrossOrigin
public class EduTeacherController {

    @Autowired
    IEduTeacherService eduTeacherService;

    @GetMapping("findAll")
    @ApiOperation(value = "所有讲师列表")
    public Result findAll() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return Result.ok().data("items", list);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "逻辑删除讲师")
    public Result deleteTeacher(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id) {
        boolean b = eduTeacherService.removeById(id);
        if (b) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    @GetMapping("pageTeacher/{current}/{limit}")
    @ApiOperation(value = "分页查询讲师")
    public Result pageListTeacher(@ApiParam(name = "current", value = "当前页", required = true) @PathVariable Long current,
                                  @ApiParam(name = "limit", value = "一页的数量", required = true) @PathVariable Long limit) {
        Page<EduTeacher> teacherPage = new Page<>(current, limit);
        eduTeacherService.page(teacherPage, null);
        long total = teacherPage.getTotal();
        List<EduTeacher> records = teacherPage.getRecords();
        return Result.ok().data("total", total).data("rows", records);
    }

    @PostMapping("pageTeacherCondition/{current}/{limit}")
    @ApiOperation(value = "条件分页查询讲师")
    public Result pageTeacherCondition(@ApiParam(name = "current", value = "当前页", required = true) @PathVariable Long current,
                                       @ApiParam(name = "limit", value = "一页的数量", required = true) @PathVariable Long limit,
                                       @RequestBody(required = false) TeacherQuery teacherQuery) {  //使用json传参。
        Page<EduTeacher> teacherPage = new Page<>(current, limit);
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();

//        try {
//            int i = 10 / 0;
//        } catch (Exception e) {
//            throw new CustomizeException(20001, "产生自定义异常咯");
//        }

        if (!StringUtils.isEmpty(teacherQuery.getName())) {
            queryWrapper.like("name", teacherQuery.getName());
        }
        if (!StringUtils.isEmpty(teacherQuery.getLevel())) {
            queryWrapper.eq("level", teacherQuery.getLevel());
        }
        if (!StringUtils.isEmpty(teacherQuery.getBegin())) {
            queryWrapper.ge("gmt_create", teacherQuery.getBegin());
        }
        if (!StringUtils.isEmpty(teacherQuery.getEnd())) {
            queryWrapper.le("gmt_create", teacherQuery.getEnd());
        }

        queryWrapper.orderByDesc("gmt_create");
        eduTeacherService.page(teacherPage, queryWrapper);
        long total = teacherPage.getTotal();
        List<EduTeacher> records = teacherPage.getRecords();
        return Result.ok().data("total", total).data("rows", records);
    }

    @PostMapping("addTeacher")
    @ApiOperation(value = "插入讲师")
    public Result addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.save(eduTeacher);
        if (save) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    @GetMapping("getTeacher/{id}")
    @ApiOperation(value = "根据id查询")
    public Result getTeacher(@PathVariable String id) {
        EduTeacher byId = eduTeacherService.getById(id);
        return Result.ok().data("teacher", byId);
    }

    @PostMapping("updateTeacher")
    @ApiOperation(value = "更新讲师信息")
    public Result updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean b = eduTeacherService.updateById(eduTeacher);
        if (b) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

}

