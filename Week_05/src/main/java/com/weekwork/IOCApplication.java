package com.weekwork;


import com.weekwork.entity.User;
import com.weekwork.jdbc.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;


@RestController
@SpringBootApplication
public class IOCApplication {

    @Autowired
    private UserDao userService;

    public static void main(String[] args) {
        SpringApplication.run(IOCApplication.class);
        System.out.println("hello ioc");
    }



    @GetMapping("/user/{id}")
    public User queryUserById(@PathVariable("id") Integer id){
        return userService.selectUserById(id);
    }

    @PostMapping("/user/save")
    public void queryUserById(@RequestBody User user){
         userService.saveUser(user);
    }
}
