package com.weekwork.rawJdbc.dynamic.service;

import com.weekwork.common.User;

public interface UserService {

    User queryUserById(int id);

    int saveUser(User user);
}
