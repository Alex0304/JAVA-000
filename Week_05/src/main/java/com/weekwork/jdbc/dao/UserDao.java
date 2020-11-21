package com.weekwork.jdbc.dao;

import com.weekwork.entity.User;

public interface UserDao {

    User selectUserById(Integer id);

    int saveUser(User user);

    int deleteById(Integer id);

    int updateUserById(User user);

    int updateUserInfo(User user);
}
