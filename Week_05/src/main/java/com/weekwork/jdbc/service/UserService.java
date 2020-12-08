package com.weekwork.jdbc.service;

import com.weekwork.common.entity.Product;
import com.weekwork.common.entity.User;

public interface UserService {

    void receiveProductMessage(Product product);

    int updateUserInfo(User user);
}
