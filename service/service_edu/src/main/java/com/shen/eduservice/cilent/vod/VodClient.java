package com.shen.eduservice.cilent.vod;

import com.shen.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: shenge
 * @Date: 2020-05-15 20:50
 * <p>
 * 删除视频服务调用
 */
@FeignClient(name = "service-vod", fallback = VodClientFeign.class)
@Component
public interface VodClient {

    @DeleteMapping("/eduvod/video/deleteVideo/{videoId}")
    public Result deleteVideo(@PathVariable("videoId") String videoId);

}
