package com.weekwork;


import com.weekwork.common.User;
import com.weekwork.common.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DynamicDatasourceTest {

    @Resource
    private UserService userService;

    @Test
    public void testQuerySlave() {
        User user = userService.queryUserById(1);
        Assert.assertEquals("slave", user.getUsername());
    }

    @Test
    public void testSaveMaster() {
        int master = userService.saveUser(new User().setUsername("master").setPassword("123456"));
        Assert.assertEquals(1, master);
    }
}
