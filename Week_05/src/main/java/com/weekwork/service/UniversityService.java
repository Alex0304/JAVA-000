package com.weekwork.service;

import org.springframework.beans.factory.InitializingBean;

public class UniversityService implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("i m  a University");
    }
}
