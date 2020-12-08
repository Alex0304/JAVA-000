package com.weekwork.jdbc.service;

import com.google.common.eventbus.Subscribe;
import com.weekwork.common.entity.Product;
import com.weekwork.common.entity.User;
import com.weekwork.jdbc.dao.UserDao;
import com.weekwork.transaction.MyTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;


    @Subscribe
    @Override
    public void receiveProductMessage(Product product) {
        System.out.println("执行监听事件的线程:"+Thread.currentThread().getName());
        log.info("新品上架了,快来浏览吧！！！"+product.getName()+":"+product.getDesc());
    }

    @Override
    @MyTransactional
    public int updateUserInfo(User user) {
        int i = userDao.updateUserInfo(user);
        if(i == 0){
            throw new RuntimeException("用户没有修改成功");
        }
        return i;
    }
}
