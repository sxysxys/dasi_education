package com.shen.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author: shenge
 * @Date: 2020-05-24 22:44
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.shen"})
@EnableFeignClients
@EnableDiscoveryClient
@EnableScheduling
@MapperScan("com.shen.staservice.mapper")
public class StaApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class,args);
    }
}
