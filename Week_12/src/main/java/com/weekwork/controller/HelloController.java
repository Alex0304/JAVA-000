package com.weekwork.controller;


import com.weekwork.entity.Address;
import com.weekwork.entity.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    //@Autowired
    // private UserMapper userMapper;

    @GetMapping("/test")
    public String hello() {
        // UserMapper mapper = Mappers.getMapper(UserMapper.class);
        Person person = new Person();
        person.setName("11");
        person.setPhone("110");
        Address address = new Address();
        address.setAddress("222");
        address.setCitycode("SHENZHEN");
        address.setCountry("baoan");
        return null;
        //JSONUtil.toJsonStr(mapper.fromPersonDTO(person,address));
    }
}
