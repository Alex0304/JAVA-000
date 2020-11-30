package com.weekwork.common;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class User extends BaseEntity<Integer> {

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户地址
     */
    private String address;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户性别
     */
    private Boolean gender;

    /**
     * 用户年龄
     */
    private Integer age;

    /**
     * 用户手机号
     */
    private String phoneNum;
}
