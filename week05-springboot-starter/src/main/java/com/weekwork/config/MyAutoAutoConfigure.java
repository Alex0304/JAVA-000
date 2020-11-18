package com.weekwork.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@ImportResource(value = "student.xml")
@Import({PersonConfig.class,SchoolConfig.class,UserConfig.class,GradeSelectorAutoConfig.class})
@ComponentScan(basePackages = "com.weekwork.service")
public class MyAutoAutoConfigure {

}
