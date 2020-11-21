package com.weekwork.transaction;


import java.lang.annotation.*;


/**
 * 自定义事务注解
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyTransactional {
}
