package com.shen.vod.controller;

import com.shen.commonutils.Result;
import com.shen.vod.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.bouncycastle.pqc.crypto.newhope.NHOtherInfoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: shenge
 * @Date: 2020-05-14 20:03
 */
@Api(description = "视频服务")
@RestController
@RequestMapping("eduvod/video")
//@CrossOrigin
public class VodController {

    @Autowired
    VodService vodService;

    @PostMapping("uploadVideo")
    @ApiOperation(value = "上传视频文件")
    public Result uploadOssFile(@ApiParam(name = "file", value = "视频小节", required = true) MultipartFile file) {
        //返回上传到oss的路径。
        String videoId = vodService.uploadVideo(file);
        return Result.ok().data("videoId", videoId);
    }

    @DeleteMapping("deleteVideo/{videoId}")
    @ApiOperation(value = "删除视频文件")
    public Result deleteVideo(@ApiParam(name = "videoId", value = "视频小节", required = true)
                              @PathVariable("videoId") String videoId) {
        vodService.deleteVideo(videoId);
        return Result.ok();
    }

    /**
     * 由源id查到播放凭证。
     * @param videoSourceId
     * @return
     */
    @GetMapping("getVideoPlay/{videoSourceId}")
    public Result getVideoPlay(@PathVariable("videoSourceId") String videoSourceId){
        String videoAuth = vodService.getVideoAuth(videoSourceId);
        return Result.ok().data("videoAuth",videoAuth);
    }

}
