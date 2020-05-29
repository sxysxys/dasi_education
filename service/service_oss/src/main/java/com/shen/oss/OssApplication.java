package com.shen.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: shenge
 * @Date: 2020-05-09 10:13
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)  //默认不注入数据库，仅仅做上传功能。
@ComponentScan(basePackages = {"com.shen"})
@EnableDiscoveryClient
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class, args);
    }
}
