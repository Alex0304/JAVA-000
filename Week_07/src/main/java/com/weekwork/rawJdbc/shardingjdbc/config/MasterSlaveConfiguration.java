/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.weekwork.rawJdbc.shardingjdbc.config;


import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.weekwork.rawJdbc.shardingjdbc.repository.AddressRepository;
import com.weekwork.rawJdbc.shardingjdbc.repository.OrderItemRepository;
import com.weekwork.rawJdbc.shardingjdbc.repository.OrderRepository;
import com.weekwork.rawJdbc.shardingjdbc.service.impl.OrderServiceImpl;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


@Configuration
public class MasterSlaveConfiguration {

    @Bean
    public DataSource dataSource(DataSource shardingMaster, DataSource shardingSlave1, DataSource shardingSlave2) throws SQLException {
        MasterSlaveRuleConfiguration masterSlaveRuleConfig = new MasterSlaveRuleConfiguration("master_slave", "master", Arrays.asList("slave1", "slave2"));
        return MasterSlaveDataSourceFactory.createDataSource(createDataSourceMap(shardingMaster, shardingSlave1, shardingSlave2), masterSlaveRuleConfig, new Properties());
    }

    private Map<String, DataSource> createDataSourceMap(DataSource shardingMaster, DataSource shardingSlave1, DataSource shardingSlave2) {
        Map<String, DataSource> result = new HashMap<>();
        result.put("master", shardingMaster);
        result.put("slave1", shardingSlave1);
        result.put("slave2", shardingSlave2);
        return result;
    }

    @Bean(name = "shardingMaster")
    @ConfigurationProperties("datasources.master")
    public DataSource shardingMaster() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "shardingSlave1")
    @ConfigurationProperties("datasources.slave1")
    public DataSource shardingSlave1() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "shardingSlave2")
    @ConfigurationProperties("datasources.slave2")
    public DataSource shardingSlave2() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "orderService")
    public OrderServiceImpl orderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, AddressRepository addressRepository) throws SQLException {
        OrderServiceImpl orderService = new OrderServiceImpl(orderRepository, orderItemRepository, addressRepository);
        orderService.initEnvironment();
        return orderService;
    }
}
