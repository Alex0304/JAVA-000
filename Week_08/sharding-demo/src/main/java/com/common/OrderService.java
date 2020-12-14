package com.common;


import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;


    public int saveUser(Order order){
        return orderMapper.save(order);
    }

    public List<Order> queryOrders(){
        return orderMapper.queryOrders();
    }
}
