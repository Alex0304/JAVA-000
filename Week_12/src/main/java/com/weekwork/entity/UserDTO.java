package com.weekwork.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDTO {

    private String userName;

    private String phone;

    private String address;

    private String city;

    private String country;

}
