package com.weekwork.mall.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weekwork.mall.entity.Order;
import com.weekwork.mall.mapper.OrderMapper;
import com.weekwork.mall.service.IOrderService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author chenhuan
 * @since 2020-11-26
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
