package com.weekwork.rawJdbc.dynamic.aop;


import java.lang.annotation.*;


/**
 * 只读注解，默认去从库读取数据
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.METHOD})
public @interface Read {
}
