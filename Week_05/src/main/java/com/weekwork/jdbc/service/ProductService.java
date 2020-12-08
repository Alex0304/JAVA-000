package com.weekwork.jdbc.service;

import com.weekwork.common.entity.Product;

import java.util.List;

public interface ProductService {

    int saveProduct(Product product);

    List<Product> queryProductList();

    Product queryProductById(int i);
}
