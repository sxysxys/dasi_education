package com.shen.eduservice.cilent.vod;

import com.shen.commonutils.Result;
import com.shen.eduservice.cilent.vod.VodClient;
import org.springframework.stereotype.Component;

/**
 * @Author: shenge
 * @Date: 2020-05-16 09:27
 */

@Component
public class VodClientFeign implements VodClient {

    @Override
    public Result deleteVideo(String videoId) {
        return Result.error().message("删除视频出错了");
    }

}
