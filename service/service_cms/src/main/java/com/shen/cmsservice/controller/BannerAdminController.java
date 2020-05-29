package com.shen.cmsservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shen.cmsservice.entity.CrmBanner;
import com.shen.cmsservice.service.ICrmBannerService;
import com.shen.commonutils.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 管理员进行banner操作crud
 * </p>
 *
 * @author shen
 * @since 2020-05-16
 */
@RestController
@RequestMapping("/cmsservice/banneradmin")
//@CrossOrigin
public class BannerAdminController {

    @Autowired
    ICrmBannerService crmBannerService;

    @GetMapping("pageBanner/{current}/{limit}")
    @ApiOperation(value = "分页查询")
    public Result pageListTeacher(@ApiParam(name = "current", value = "当前页", required = true) @PathVariable Long current,
                                  @ApiParam(name = "limit", value = "一页的数量", required = true) @PathVariable Long limit) {
        Page<CrmBanner> teacherPage = new Page<>(current, limit);
        crmBannerService.page(teacherPage, null);
        long total = teacherPage.getTotal();
        List<CrmBanner> records = teacherPage.getRecords();
        return Result.ok().data("rows", records).data("total", total);
    }

    @PostMapping("addBanner")
    @ApiOperation(value = "插入讲师")
    public Result addTeacher(@RequestBody CrmBanner crmBanner) {
        boolean save = crmBannerService.save(crmBanner);
        if (save) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    @GetMapping("getBanner/{id}")
    @ApiOperation(value = "根据id查询")
    public Result getTeacher(@PathVariable String id) {
        CrmBanner byId = crmBannerService.getById(id);
        return Result.ok().data("teacher", byId);
    }

    @PostMapping("updateBanner")
    @ApiOperation(value = "更新banner信息")
    public Result updateTeacher(@RequestBody CrmBanner crmBanner) {
        boolean b = crmBannerService.updateById(crmBanner);
        if (b) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "逻辑删除banner")
    public Result deleteTeacher(@ApiParam(name = "id", value = "bannerID", required = true) @PathVariable String id) {
        boolean b = crmBannerService.removeById(id);
        if (b) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

}

