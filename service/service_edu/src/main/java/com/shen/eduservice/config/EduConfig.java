package com.shen.eduservice.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: shenge
 * @Date: 2020-05-05 23:43
 */
@Configuration
@MapperScan("com.shen.eduservice.mapper")
public class EduConfig {
}

