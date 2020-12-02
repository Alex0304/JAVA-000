package com.weekwork.rawJdbc.dynamic.service;

import org.springframework.beans.factory.InitializingBean;

public class GradeService implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(" i m a gradeService");
    }
}
