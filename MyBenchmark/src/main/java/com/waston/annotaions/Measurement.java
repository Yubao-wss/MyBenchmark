package com.waston.annotaions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 测量实验注解
 * @Author: Waston
 * @Date: 2019/7/12 19:46
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Measurement {
    //一组实验调用方法多少次
    int iterations();
    //进行多少组实验
    int countPerGroup();
}
