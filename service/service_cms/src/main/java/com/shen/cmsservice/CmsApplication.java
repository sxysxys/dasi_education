package com.shen.cmsservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: shenge
 * @Date: 2020-05-16 16:08
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = "com.shen")  //为了将其他模块的Config扫描进来，如果不配这个只会扫描本模块下的。
@MapperScan("com.shen.cmsservice.mapper")
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
