package com.common;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper {
    @Insert("INSERT INTO t_order (user_id, address_id, status) VALUES (#{userId,jdbcType=INTEGER}, #{addressId,jdbcType=BIGINT}, #{status,jdbcType=VARCHAR})")
    int save(Order order);

    @Select("SELECT order_id 'orderId',user_id 'userId', address_id 'addressId',status FROM t_order")
    List<Order> queryOrders();
}
