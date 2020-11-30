package com.weekwork.jdbc.service;


import com.google.common.eventbus.EventBus;
import com.weekwork.entity.Product;
import com.weekwork.eventbus.ProductEvent;
import com.weekwork.jdbc.dao.ProductDao;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service(value = "productService")
public class ProductServiceImpl implements ProductService {

    private static final String INSERT_SQL = "insert into t_product(name,`desc`,price) values (?,?,?)";


    /**
     * 注入hikariDatasource
     */
    @Autowired
    private HikariDataSource dataSource;

    @Resource
    private ProductDao productDao;

    @Resource
    private EventBus productEventBus;


    /**
     * 使用hikari连接池获取连接，操作数据库
     */
    @Override
    public int saveProduct(Product product) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_SQL);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDesc());
            preparedStatement.setDouble(3, product.getPrice());
            int i = preparedStatement.executeUpdate();
            //新增商品,对外发布事件，通知userService
            System.out.println("发布事件的线程:"+Thread.currentThread().getName());
            productEventBus.post(new ProductEvent(product));
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public List<Product> queryProductList() {
        return productDao.queryProductList();
    }

    @Override
    public Product queryProductById(int id) {
        return productDao.queryProductById(id);
    }
}
