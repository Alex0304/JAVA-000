package com.weekwork.dynamic.service;

import org.springframework.beans.factory.BeanNameAware;

public class PersonService implements BeanNameAware {

    private String beanName;
    @Override
    public void setBeanName(String s) {
        this.beanName = s;
        //System.out.println("my name is "+beanName);
    }

    private void init(){
        System.out.println("i m a personService");
    }
}
