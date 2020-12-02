package com.weekwork;


import com.weekwork.common.Order;
import com.weekwork.common.User;
import com.weekwork.rawJdbc.dynamic.service.UserService;
import com.weekwork.rawJdbc.shardingjdbc.repository.OrderRepository;
import com.weekwork.rawJdbc.shardingjdbc.service.impl.OrderServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DynamicDatasourceTest {

    @Resource
    private UserService userService;

    @Resource
    private OrderRepository orderRepository;

    @Test
    public void testQuerySlave() {
        User user = userService.queryUserById(1);
        Assert.assertEquals("slave", user.getUserName());
    }

    @Test
    public void testSaveMaster() {
        User user = new User();
        user.setUserName("master");
        user.setPwd("123456");
        int master = userService.saveUser(user);
        Assert.assertEquals(1, master);
    }

    @Test
    public void testShardInsert() throws SQLException {
        orderRepository.insert(new Order().setUserId(1).setOrderId(1).setAddressId(123).setStatus("UN_PAY"));
        orderRepository.insert(new Order().setUserId(2).setOrderId(2).setAddressId(456).setStatus("UN_PAY"));
        orderRepository.insert(new Order().setUserId(3).setOrderId(3).setAddressId(789).setStatus("UN_PAY"));
    }

    @Test
    public void testShardQuery() throws SQLException {
        List<Order> order1 = orderRepository.selectAll();
        order1.stream().map(e -> e.getAddressId()).forEach(System.out::println);
        List<Order> order2 = orderRepository.selectAll();
        order2.stream().map(e -> e.getAddressId()).forEach(System.out::println);
    }
}
