package com.shen.vod.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: shenge
 * @Date: 2020-05-14 20:06
 */
public interface VodService {
    String uploadVideo(MultipartFile file);

    void deleteVideo(String videoId);

    String getVideoAuth(String videoSourceId);
}
