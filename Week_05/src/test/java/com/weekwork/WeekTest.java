package com.weekwork;


import com.weekwork.common.entity.Product;
import com.weekwork.common.entity.User;
import com.weekwork.jdbc.dao.UserDao;
import com.weekwork.jdbc.service.ProductService;
import com.weekwork.jdbc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = IOCApplication.class)
public class WeekTest {

    @Autowired
    private UserDao userDao;

    @Resource
    private ProductService productService;

    @Resource
    private UserService userService;

    @Test
    public void query(){
        User user = userDao.selectUserById(1);
        System.out.println(user.getName());
    }


    @Test
    public void insert(){
        User jack = new User().setName("Jack").setPassword("111111");
        int i = userDao.saveUser(jack);
        Assert.assertEquals(1,i);
    }

    @Test
    public void update(){
        User jack = new User().setId(6).setPassword("222222");
        int i = userDao.updateUserById(jack);
        Assert.assertEquals(1,i);

    }

    @Test
    public void delete(){
        int i = userDao.deleteById(6);
        Assert.assertEquals(1,i);
    }

    @Test
    public void saveProduct(){
        //Product product = new Product().setName("智能手机").setDesc("iphone12").setPrice(6599.0);
        Product product = new Product().setName("电风扇").setDesc("美的遥控静音电风扇").setPrice(299.0);
        int i = productService.saveProduct(product);
        Assert.assertEquals(1,i);
    }

    @Test
    public void queryProductList(){
        List<Product> products = productService.queryProductList();
        Assert.assertEquals(2,products.size());
    }

    @Test
    public void queryProductById(){
        Product product = productService.queryProductById(1);
        Assert.assertEquals(1,product.getId());
    }

    @Test
    public void updateUserInfo(){
        User user = new User().setId(1).setPassword("000013");
        userService.updateUserInfo(user);
    }
}
