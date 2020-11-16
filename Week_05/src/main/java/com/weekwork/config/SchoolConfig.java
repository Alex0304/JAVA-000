package com.weekwork.config;


import com.weekwork.service.SchoolService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;



@Configuration
public class SchoolConfig {

    @Bean(initMethod = "init")
    public SchoolService schoolService(){
        return new SchoolService();
    }
}
