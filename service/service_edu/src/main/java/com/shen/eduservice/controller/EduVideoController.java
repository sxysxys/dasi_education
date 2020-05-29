package com.shen.eduservice.controller;


import com.shen.commonutils.Result;
import com.shen.eduservice.cilent.vod.VodClient;
import com.shen.eduservice.entity.EduVideo;
import com.shen.eduservice.service.IEduVideoService;
import com.shen.servicebase.exception.CustomizeException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author shen
 * @since 2020-05-11
 */
@RestController
@RequestMapping("/eduservice/video")
//@CrossOrigin
public class EduVideoController {

    @Autowired
    IEduVideoService eduVideoService;

    @Autowired
    VodClient vodClient;

    @PostMapping("addVideo")
    @ApiOperation(value = "添加视频")
    public Result addVideo(@RequestBody EduVideo eduVideo) {
        if (!eduVideoService.save(eduVideo)) {
            throw new CustomizeException(20001, "添加视频失败");
        }
        return Result.ok();
    }

    @GetMapping("getVideoInfo/{videoId}")
    @ApiOperation(value = "查询某个小节")
    public Result getVideoInfo(@PathVariable("videoId") String videoId) {
        EduVideo eduVideo = eduVideoService.getById(videoId);
        return Result.ok().data("video", eduVideo);
    }

    @PostMapping("updateVideo")
    @ApiOperation(value = "更新某个小节")
    public Result updateVideo(@RequestBody EduVideo eduVideo) {
        if (!eduVideoService.updateById(eduVideo)) {
            throw new CustomizeException(20001, "更新视频失败");
        }
        return Result.ok();
    }


    @DeleteMapping("deleteVideo/{videoId}")
    @ApiOperation(value = "删除某个小节，并且删除视频")
    public Result deleteVideo(@PathVariable("videoId") String videoId) {
        //需要根据小节id，查出视频id，再一起进行删除
        EduVideo one = eduVideoService.getById(videoId);
        String videoSourceId = one.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            Result result = vodClient.deleteVideo(videoSourceId);
            // 是否执行熔断器。
            if (result.getCode() == 20001) {
                throw new CustomizeException(20001, "删除视频失败");
            }
        }
        if (!eduVideoService.removeById(videoId)) {
            throw new CustomizeException(20001, "删除视频数据失败");
        }
        return Result.ok();
    }
}

