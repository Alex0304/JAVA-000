package com.weekwork.eventbus;

import com.google.common.eventbus.Subscribe;
import com.weekwork.jdbc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ProductEventListener {

    @Autowired
    private UserService userService;

    @Subscribe
    public void listen(ProductEvent productEvent){
        userService.receiveProductMessage(productEvent.getProduct());
    }

}
