package com.shen.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: shenge
 * @Date: 2020-05-14 13:53
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)  //默认不注入数据库，仅仅做上传功能。
@ComponentScan(basePackages = {"com.shen"})  //这里一配置，只要项目跑起来，不管是测试类还是VodApplication都会去扫描。
public class VodApplication {
    public static void main(String[] args) {
        SpringApplication.run(VodApplication.class, args);
    }
}
