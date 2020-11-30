package com.weekwork.common;

public interface UserService {

    User queryUserById(int id);

    int saveUser(User user);
}
