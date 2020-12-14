package com.common;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper extends BaseMapper<Order> {
    @Insert("INSERT INTO t_order (user_id, address_id, status) VALUES (#{userId,jdbcType=INTEGER}, #{addressId,jdbcType=BIGINT}, #{status,jdbcType=VARCHAR})")
    int save(Order order);

    @Select("SELECT * FROM t_order;")
    List<Order> queryOrders();
}
