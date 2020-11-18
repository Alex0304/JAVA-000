package com.weekwork.service;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Component
public class ClassService {

    @PostConstruct
    public void init(){
        System.out.println("i m a Class");
    }

}
