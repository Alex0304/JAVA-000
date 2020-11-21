package com.weekwork.proxy;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.METHOD})
public @interface MySelect {
    String value();
}
