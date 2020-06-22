package com.oyyo.eduService.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.oyyo.eduService.mapper")
public class EduConfig {

}
