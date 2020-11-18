package com.weekwork.jdbc;

import com.weekwork.entity.User;

public interface UserService {

    User selectUserById(Integer id);

    void saveUser(User user);
}
