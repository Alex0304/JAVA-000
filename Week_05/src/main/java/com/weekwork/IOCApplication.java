package com.weekwork;


import com.weekwork.config.GradeSelectorAutoConfig;
import com.weekwork.config.PersonConfig;
import com.weekwork.service.HelloService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@ImportResource(value = "student.xml")
@SpringBootApplication
@RestController
@Import({GradeSelectorAutoConfig.class, PersonConfig.class})
public class IOCApplication {

    public static void main(String[] args) {
        SpringApplication.run(new Class[]{IOCApplication.class,HelloService.class},args);
        System.out.println("xxxxx");
    }



}
