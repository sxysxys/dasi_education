package com.shen.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: shenge
 * @Date: 2020-05-09 11:25
 */
public interface OssService {
    String uploadAvatar(MultipartFile file);
}
