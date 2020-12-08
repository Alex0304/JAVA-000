package com.weekwork;


import com.weekwork.common.Order;
import com.weekwork.common.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShardingTest {


    @Resource
    private OrderService orderService;

    @Test
    public void test1(){
        //final Order order = new Order().setUserId(111).setAddressId(1111).setStatus("UN_PAY");
        final Order order1 = new Order().setUserId(111).setAddressId(1111).setStatus("UN_PAY");
        final Order order2 = new Order().setUserId(112).setAddressId(1111).setStatus("UN_PAY");
        final Order order3 = new Order().setUserId(113).setAddressId(1111).setStatus("UN_PAY");
        final Order order4 = new Order().setUserId(114).setAddressId(1111).setStatus("UN_PAY");
        final Order order5 = new Order().setUserId(118).setAddressId(1111).setStatus("UN_PAY");
        orderService.saveUser(order2);
        orderService.saveUser(order3);
        orderService.saveUser(order4);
        orderService.saveUser(order5);
    }

    @Test
    public void test2() {
        final List<Order> orders = orderService.queryOrders();
        Optional.ofNullable(orders).orElse(new ArrayList<>()).stream().forEach(e -> System.out.println(e.getOrderId()));
    }

    @Test
    public void test3(){
        //final Order order = new Order().setUserId(111).setAddressId(1111).setStatus("UN_PAY");
        final Order order1 = new Order().setUserId(211).setAddressId(1111).setStatus("UN_PAY");
        final Order order2 = new Order().setUserId(212).setAddressId(1111).setStatus("UN_PAY");
        final Order order3 = new Order().setUserId(213).setAddressId(1111).setStatus("UN_PAY");
        final Order order4 = new Order().setUserId(214).setAddressId(1111).setStatus("UN_PAY");
        final Order order5 = new Order().setUserId(218).setAddressId(1111).setStatus("UN_PAY");
        orderService.saveUser(order2);
        orderService.saveUser(order1);
        orderService.saveUser(order3);
        orderService.saveUser(order4);
        orderService.saveUser(order5);
    }
}
