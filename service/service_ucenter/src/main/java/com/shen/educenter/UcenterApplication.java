package com.shen.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: shenge
 * @Date: 2020-05-19 21:11
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.shen"})
@MapperScan("com.shen.educenter.mapper")
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class,args);
    }
}
