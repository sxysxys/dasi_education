package com.shen.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: shenge
 * @Date: 2020-05-27 10:40
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GateApplication {
    public static void main(String[] args) {
        SpringApplication.run(GateApplication.class,args);
    }
}
