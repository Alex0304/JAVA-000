package com.weekwork.config;


import com.weekwork.rawJdbc.dynamic.service.SchoolService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SchoolConfig {

    @Bean(initMethod = "init")
    public SchoolService schoolService(){
        return new SchoolService();
    }
}
