package com.weekwork;


import com.weekwork.mall.entity.Order;
import com.weekwork.mall.mapper.OrderMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MallOrderTest {

    @Resource
    private OrderMapper orderMapper;

    @Test
    public void testSaveOrder() {
        int un_pay = orderMapper.insert(new Order().setOrderNum("202011261122").setUserId(1).setOrderStatus("UN_PAY"));
        Assert.assertEquals(1, un_pay);
    }

    @Test
    public void testSelectOrder() {
        Order order = orderMapper.selectById(1);
        Assert.assertNotNull(order);
    }

    @Test
    public void testUpdateOrder() {
        int payed = orderMapper.updateById(new Order().setId(1).setOrderStatus("PAYED"));
        Assert.assertEquals(1, payed);
    }

    @Test
    public void testDeleteOrder() {
        int i = orderMapper.deleteById(1);
        Assert.assertEquals(1, i);
    }
}
