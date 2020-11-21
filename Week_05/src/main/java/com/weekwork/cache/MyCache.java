package com.weekwork.cache;


import java.lang.annotation.*;

/**
 * 自定义事务注解--使用google 的guuava作为自定义进程内缓存
 */
@Target({ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface MyCache {

}
