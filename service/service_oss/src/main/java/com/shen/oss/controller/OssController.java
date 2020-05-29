package com.shen.oss.controller;

import com.shen.commonutils.Result;
import com.shen.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: shenge
 * @Date: 2020-05-09 11:25
 * <p>
 * 上传文件接口。
 */
@Api(description = "上传文件")
@RestController
@RequestMapping("/eduoss/fileoss")
//@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;


    @PostMapping
    @ApiOperation(value = "上传头像文件")
    public Result uploadOssFile(@ApiParam(name = "file", value = "头像", required = true) MultipartFile file) {
        //返回上传到oss的路径。
        String url = ossService.uploadAvatar(file);
        return Result.ok().data("url", url);
    }
}
