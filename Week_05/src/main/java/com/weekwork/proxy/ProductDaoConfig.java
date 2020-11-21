package com.weekwork.proxy;


import cn.hutool.aop.ProxyUtil;
import cn.hutool.aop.aspects.Aspect;
import cn.hutool.aop.aspects.SimpleAspect;
import cn.hutool.core.util.ReflectUtil;
import com.google.common.reflect.Reflection;
import com.weekwork.jdbc.dao.ProductDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.lang.reflect.Proxy;

@Configuration
public class ProductDaoConfig {


    /**
     * 使用原生的jdk动态代理生成mapper 代理对象
     * @return
     */
    @Bean
    public ProductDao productDao(@Qualifier("dataSource") DataSource dataSource){
        ProductDaoProxy productDaoProxy = new ProductDaoProxy(dataSource);
        return (ProductDao)Proxy.newProxyInstance(ProductDao.class.getClassLoader(),new Class[]{ProductDao.class},productDaoProxy);
    }

    @Bean
    public ProductDao productDao1(@Qualifier("dataSource") DataSource dataSource){
        ProductDaoProxy productDaoProxy = new ProductDaoProxy(dataSource);
        return ProxyUtil.newProxyInstance(productDaoProxy,ProductDao.class);
    }

    @Bean
    public ProductDao productDao2(@Qualifier("dataSource") DataSource dataSource){
        return Reflection.newProxy(ProductDao.class,new ProductDaoProxy(dataSource));
    }
}
