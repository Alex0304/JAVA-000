package com.weekwork.entity;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Person {

    private String name;

    private String phone;

}
