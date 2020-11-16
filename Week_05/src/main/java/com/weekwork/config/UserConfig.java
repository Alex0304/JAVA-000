package com.weekwork.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public HelloBeanFactoryPostProcessor helloBeanFactoryPostProcessor(){
        return new HelloBeanFactoryPostProcessor();
    }
}
