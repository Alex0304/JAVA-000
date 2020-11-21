package com.weekwork.jdbc.service;

import com.weekwork.entity.Product;
import com.weekwork.entity.User;
import com.weekwork.eventbus.ProductEvent;

public interface UserService {

    void receiveProductMessage(Product product);

    int updateUserInfo(User user);
}
