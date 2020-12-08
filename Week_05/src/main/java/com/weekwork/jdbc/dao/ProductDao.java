package com.weekwork.jdbc.dao;

import com.weekwork.common.entity.Product;
import com.weekwork.proxy.MySelect;

import java.util.List;

public interface ProductDao {

    @MySelect("select id,name,`desc`,price from t_product")
    List<Product> queryProductList();

    @MySelect("select id,name,`desc`,price from t_product where id = {0}")
    Product queryProductById(Integer id);

}
