package com.weekwork.dynamic.service;


import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ClassService {

    @PostConstruct
    public void init(){
        System.out.println("i m a Class");
    }

}
