package com.oyyo.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
//扫描 swagger 配置类
@ComponentScan(basePackages = {"com.oyyo"})
@MapperScan("com.oyyo.educms.mapper")
public class cmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(cmsApplication.class, args);
    }
}
