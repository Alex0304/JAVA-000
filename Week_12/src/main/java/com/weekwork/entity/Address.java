package com.weekwork.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Address {

    private String country;

    private String citycode;

    private String address;

}
