package com.weekwork.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@ImportResource(value = "classpath*:/student.xml")
@Import({PersonConfig.class,SchoolConfig.class,UserConfig.class,GradeSelectorAutoConfig.class})
@ComponentScan(basePackages = "com.weekwork.rawJdbc.dynamic.service")
public class MyAutoAutoConfigure {

}
