package com.weekwork.common.entity;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Product {

    private int id;

    private String name;

    private String desc;

    private double price;
}
